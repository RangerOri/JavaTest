import Algorithms.*;
import Exceptions.*;
import EncryptionIO.FileIO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Ori on 26/09/2015.
 */
public class UnitTesting {

    private int[] test;// = {'1','a','5','7','8','\'', '\"'};
    private int[] result = test;

    public UnitTesting(){

        test = new int[96];
        for (int c=0; c<96; c++) {
            test[c] = (c+32);
        }
    }

    public void test(EncryptionAlgorithm testAlgorithm){
        try {
            System.out.println("Testing algorithm: " + testAlgorithm.getClass().getName());
            Assert.assertNotNull("is null!", testAlgorithm);
            result = testAlgorithm.encrypt(test);
            Assert.assertTrue("Test isn't encrypted, key given:"+testAlgorithm.getKey(), !Arrays.equals(test, result));
            result = testAlgorithm.decrypt(result, testAlgorithm.getKey());
            Assert.assertTrue("Test decryption has failed, key given:"+testAlgorithm.getKey(), Arrays.equals(test, result));
            System.out.println("Testing algorithm: " + testAlgorithm.getClass().getName() + " done.");
        } catch (InvalidEncryptionKeyException e) {
            System.out.println(e.getKeyError());
        }
    }

    @Test
    public void testShiftUp() {
        test(new ShiftUpEncryption());
    }

    @Test
    public void testMultiply() {
        test(new ShiftMultiplyEncryption());
    }

    @Test
    public void testXor() {
        test(new XorEncryption());
    }

    @Test
    public void testRepeat() {
        test(new RepeatEncryption(4, new ShiftUpEncryption()));
    }

    @Test(expected = InvalidEncryptionKeyException.class)
    public void testKeyExcpetion() throws InvalidEncryptionKeyException{
        System.out.println("Testing invalid key exception");
        new ShiftUpEncryption().decrypt(null, 0);
    }

    @Test(expected = InvalidPathException.class)
    public void testPathExcpetion() throws InvalidPathException{
        System.out.println("Testing invalid path exception");
        FileIO.validateFilePath("check.asv");
    }

    @Test
    public void testKeys() {
        System.out.println("Testing different key strengths");
        EncryptionAlgorithm algos[] = new EncryptionAlgorithm[] {new ShiftMultiplyEncryption(), new ShiftUpEncryption(), new XorEncryption()};
        Assert.assertTrue(algos[0] != algos[1]); //based on comparator, should compare key strengths
        Assert.assertTrue(algos[1] != algos[2]);
        Assert.assertTrue(algos[0] != algos[2]);
    }
}
