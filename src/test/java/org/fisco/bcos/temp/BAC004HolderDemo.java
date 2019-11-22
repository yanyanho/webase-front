package org.fisco.bcos.temp;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class BAC004HolderDemo extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b5060405160208061064c83398101806040528101908080519060200190929190505050806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166329965a1d3060405180807f42414330303441737365747353656e646572000000000000000000000000000081525060120190506040518091039020306040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183600019166000191681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019350505050600060405180830381600087803b1580156101a857600080fd5b505af11580156101bc573d6000803e3d6000fd5b505050506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166329965a1d3060405180807f424143303034417373657473526563697069656e74000000000000000000000081525060150190506040518091039020306040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183600019166000191681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019350505050600060405180830381600087803b1580156102f557600080fd5b505af1158015610309573d6000803e3d6000fd5b505050505061032f8061031d6000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063249cb3fa1461005c57806399eb18f8146100c9578063d2aefe6114610186575b600080fd5b34801561006857600080fd5b506100ab6004803603810190808035600019169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610243565b60405180826000191660001916815260200191505060405180910390f35b3480156100d557600080fd5b50610184600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019082018035906020019190919293919293908035906020019082018035906020019190919293919293905050506102ef565b005b34801561019257600080fd5b50610241600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019082018035906020019190919293919293908035906020019082018035906020019190919293919293905050506102f9565b005b600060405160200180807f42414352454749535445525f4143434550545f4d41474943000000000000000081525060180190506040516020818303038152906040526040518082805190602001908083835b6020831015156102ba5780518252602082019150602081019050602083039250610295565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020905092915050565b5050505050505050565b50505050505050505600a165627a7a7230582071a1ee186433ff6691239339abc047190c4d58d9d2b5992f669f30660d049f3a0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"interfaceHash\",\"type\":\"bytes32\"},{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"canImplementInterfaceForAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"operator\",\"type\":\"address\"},{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"userData\",\"type\":\"bytes\"},{\"name\":\"operatorData\",\"type\":\"bytes\"}],\"name\":\"assetsToSend\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"operator\",\"type\":\"address\"},{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"},{\"name\":\"userData\",\"type\":\"bytes\"},{\"name\":\"operatorData\",\"type\":\"bytes\"}],\"name\":\"assetsReceived\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"registerAddress\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"}]";

    public static final String FUNC_CANIMPLEMENTINTERFACEFORADDRESS = "canImplementInterfaceForAddress";

    public static final String FUNC_ASSETSTOSEND = "assetsToSend";

    public static final String FUNC_ASSETSRECEIVED = "assetsReceived";

    @Deprecated
    protected BAC004HolderDemo(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BAC004HolderDemo(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BAC004HolderDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BAC004HolderDemo(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<byte[]> canImplementInterfaceForAddress(byte[] interfaceHash, String addr) {
        final Function function = new Function(FUNC_CANIMPLEMENTINTERFACEFORADDRESS, 
                Arrays.<Type>asList(new Bytes32(interfaceHash),
                new org.fisco.bcos.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> assetsToSend(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData) {
        final Function function = new Function(
                FUNC_ASSETSTOSEND, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void assetsToSend(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ASSETSTOSEND, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String assetsToSendSeq(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData) {
        final Function function = new Function(
                FUNC_ASSETSTOSEND, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> assetsReceived(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData) {
        final Function function = new Function(
                FUNC_ASSETSRECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void assetsReceived(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ASSETSRECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String assetsReceivedSeq(String operator, String from, String to, BigInteger amount, byte[] userData, byte[] operatorData) {
        final Function function = new Function(
                FUNC_ASSETSRECEIVED, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(from), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(to), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(userData), 
                new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(operatorData)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static BAC004HolderDemo load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BAC004HolderDemo(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BAC004HolderDemo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BAC004HolderDemo(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BAC004HolderDemo load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BAC004HolderDemo(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BAC004HolderDemo load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BAC004HolderDemo(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BAC004HolderDemo> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String registerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(registerAddress)));
        return deployRemoteCall(BAC004HolderDemo.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<BAC004HolderDemo> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String registerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(registerAddress)));
        return deployRemoteCall(BAC004HolderDemo.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BAC004HolderDemo> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String registerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(registerAddress)));
        return deployRemoteCall(BAC004HolderDemo.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BAC004HolderDemo> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String registerAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(registerAddress)));
        return deployRemoteCall(BAC004HolderDemo.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
