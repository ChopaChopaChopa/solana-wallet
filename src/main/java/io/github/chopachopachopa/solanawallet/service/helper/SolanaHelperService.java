package io.github.chopachopachopa.solanawallet.service.helper;

import io.github.chopachopachopa.solanawallet.domain.model.Transfer;
import io.github.chopachopachopa.solanawallet.exception.CommonException;
import io.github.chopachopachopa.solanawallet.exception.ErrorCode;
import io.github.chopachopachopa.solanawallet.util.SolConverter;
import io.github.chopachopachopa.solanawallet.util.mapper.TransferMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.rpc.RpcApi;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;
import org.p2p.solanaj.rpc.types.SignatureInformation;
import org.p2p.solanaj.rpc.types.config.Commitment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolanaHelperService {
    private final RpcClient client;
    private final TransferMapper transferMapper;

    public BigDecimal getBalance(final String address) {
        try {
            return SolConverter.toSol(
                getApi().getBalance(new PublicKey(address))
            );
        } catch (RpcException e) {
            throw new CommonException(ErrorCode.BALANCE);
        }
    }

    public String sendTransaction(
        final String secretKey,
        final String toAddress,
        final BigDecimal amount
    ) {
        try {
            val account = new Account(Base58.decode(secretKey));
            val lamports = SolConverter.toLamports(amount);

            val instruction = SystemProgram.transfer(account.getPublicKey(), new PublicKey(toAddress), lamports);

            Transaction transaction = new Transaction();
            transaction.addInstruction(instruction);

            return getApi().sendTransaction(transaction, account);
        } catch (RpcException e) {
            throw new CommonException(ErrorCode.TRANSFER);
        }
    }

    public List<Transfer> getTransfers(final String address, int limit) {
        val publicKey = new PublicKey(
            Base58.decode(address)
        );

        try {
            val signatures = getApi().getSignaturesForAddress(publicKey, limit, Commitment.CONFIRMED);
            val list = new ArrayList<Transfer>();

            for (SignatureInformation signatureInformation : signatures) {
                val signature = signatureInformation.getSignature();
                val transaction = getApi().getTransaction(signature, Commitment.CONFIRMED);
                list.add(transferMapper.toModel(transaction));
            }

            return list;
        } catch (RpcException e) {
            throw new CommonException(ErrorCode.TRANSFER_LIST);
        }
    }

    private RpcApi getApi() {
        return client.getApi();
    }
}
