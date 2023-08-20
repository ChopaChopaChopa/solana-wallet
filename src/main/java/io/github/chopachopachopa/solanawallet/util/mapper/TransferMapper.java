package io.github.chopachopachopa.solanawallet.util.mapper;

import io.github.chopachopachopa.solanawallet.domain.model.Transfer;
import io.github.chopachopachopa.solanawallet.dto.TransferDto;
import io.github.chopachopachopa.solanawallet.util.SolConverter;
import java.math.BigDecimal;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.p2p.solanaj.rpc.types.ConfirmedTransaction;

@Mapper(componentModel = "spring")
public abstract class TransferMapper {
    public abstract TransferDto toDto(Transfer model);

    @Mapping(target = "fromAddress", source = "tx.transaction", qualifiedByName = "extractFromAddress")
    @Mapping(target = "toAddress", source = "tx.transaction", qualifiedByName = "extractToAddress")
    @Mapping(target = "amount", source = "tx.meta", qualifiedByName = "calculateAmount")
    @Mapping(target = "fee", source = "tx.meta", qualifiedByName = "extractFee")
    @Mapping(target = "signature", source = "tx.transaction", qualifiedByName = "extractSignature")
    public abstract Transfer toModel(ConfirmedTransaction tx);

    @Named("extractFromAddress")
    protected static String extractFromAddress(ConfirmedTransaction.Transaction transaction) {
        return transaction.getMessage().getAccountKeys().get(0);
    }

    @Named("extractToAddress")
    protected static String extractToAddress(ConfirmedTransaction.Transaction transaction) {
        return transaction.getMessage().getAccountKeys().get(1);
    }

    @Named("extractSignature")
    protected static String extractSignature(ConfirmedTransaction.Transaction transaction) {
        return transaction.getSignatures().get(0);
    }

    @Named("extractFee")
    protected static BigDecimal extractFee(ConfirmedTransaction.Meta meta) {
        return SolConverter.toSol(meta.getFee());
    }

    @Named("calculateAmount")
    protected static BigDecimal calculateAmount(ConfirmedTransaction.Meta meta) {
        val preBalances = meta.getPreBalances().get(1);
        val postBalances = meta.getPostBalances().get(1);
        val lamports = Math.abs(preBalances - postBalances);
        return SolConverter.toSol(lamports);
    }
}
