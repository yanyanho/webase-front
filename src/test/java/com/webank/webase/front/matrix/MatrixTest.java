package com.webank.webase.front.matrix;

import com.webank.webase.front.base.Constants;
import com.webank.webase.front.channel.test.Ok;
import com.webank.webase.front.channel.test.TestBase;
import com.webank.webase.front.contract.CommonContract;
import com.webank.webase.front.contract.ContractService;
import com.webank.webase.front.transaction.TransService;
import org.fisco.bcos.temp.EvidenceSignersData;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.precompile.cns.CnsService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.webank.webase.front.util.AbiUtil.outputFormat;
import static com.webank.webase.front.util.ContractAbiUtil.contractEventMap;
import static org.junit.Assert.assertEquals;

public class MatrixTest extends TestBase {


    @Test
    public void testChat() throws Exception {

        System.out.println("************");
    }


}

