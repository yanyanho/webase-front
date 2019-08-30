/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package com.webank.webase.front.web3j;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import com.webank.webase.front.util.Tools;
import org.apache.commons.codec.binary.Hex;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.solidity.Abi;
import org.junit.Test;

public class AbiTest {

    @Test
    public void test() {

     byte[] hash1 = Hash.sha256(Tools.stringToByte32Array("hello1"));
      System.out.println(Util.getHexString(hash1,false));
     String  hashchain ="e2fc2924d46d00f40b5a9a2788cffe77b48d7ef03ab809966e6ccbd4f3d29490";
     String  hashchain1="0ee8c396eb4005554d2ee590de052203711496512b848db0dd8a380df0fcde20";
       byte[] hashchi = Util.hexStringToBytes(hashchain);
      System.out.println(1);
    }


  @Test
  public void testGenerateKey() throws Exception {
    Credentials credentials = Credentials.create("3bed914595c159cbce70ec5fb6aff3d6797e0c5ee5a7a9224a21cae8932d84a4");
    System.out.println( credentials.getAddress());
    System.out.println( credentials.getEcKeyPair().getPrivateKey());
    System.out.println(  credentials.getEcKeyPair().getPublicKey());

  }
  @Test
  public void simpleTest() throws IOException {
    String contractAbi =
        "[{"
            + "\"name\":\"simpleFunction\","
            + "\"constant\":true,"
            + "\"payable\":true,"
            + "\"type\":\"function\","
            + "\"inputs\": [{\"name\":\"_in\", \"type\":\"bytes32\"}],"
            + "\"outputs\":[{\"name\":\"_out\",\"type\":\"bytes32\"}]}]";

    Abi abi = Abi.fromJson(contractAbi);
    assertEquals(abi.size(), 1);

    Abi.Entry onlyFunc = abi.get(0);
    assertEquals(onlyFunc.type, Abi.Entry.Type.function);
    assertEquals(onlyFunc.inputs.size(), 1);
    assertEquals(onlyFunc.outputs.size(), 1);
    assertTrue(onlyFunc.payable);
    assertTrue(onlyFunc.constant);
  }

  @Test
  public void simpleLegacyTest() throws IOException {
    String contractAbi =
        "[{"
            + "\"name\":\"simpleFunction\","
            + "\"constant\":true,"
            + "\"type\":\"function\","
            + "\"inputs\": [{\"name\":\"_in\", \"type\":\"bytes32\"}],"
            + "\"outputs\":[{\"name\":\"_out\",\"type\":\"bytes32\"}]}]";

    Abi abi = Abi.fromJson(contractAbi);
    assertEquals(abi.size(), 1);

    Abi.Entry onlyFunc = abi.get(0);
    assertEquals(onlyFunc.type, Abi.Entry.Type.function);
    assertEquals(onlyFunc.inputs.size(), 1);
    assertEquals(onlyFunc.outputs.size(), 1);
    assertTrue(onlyFunc.constant);
  }
}
