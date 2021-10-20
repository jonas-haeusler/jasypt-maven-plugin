package dev.haeusler.mojo;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DecryptPropertyMojoTest {

    private static final String jasyptEncryptorPassword = "super_secret_passw0rd";

    private static Log log;

    private static DecryptPropertyMojo decryptPropertyMojo;

    @BeforeAll
    static void setUp() {
        decryptPropertyMojo = new DecryptPropertyMojo();

        // make sure we always have the same encrypted value
        decryptPropertyMojo.setSaltGeneratorClassName("org.jasypt.salt.ZeroSaltGenerator");
        decryptPropertyMojo.setIvGeneratorClassName("dev.haeusler.mojo.FixedStringIvGenerator");

        decryptPropertyMojo.setJasyptEncryptorPassword(jasyptEncryptorPassword);

        log = mock(Log.class);
        decryptPropertyMojo.setLog(log);
    }

    @Test
    public void testEncrypt() {
        final String expectedDecryptedValue = "value-to-encrypt";
        final String valueToDecrypt = "ENC(jMI5t0UiN2UJ3do70j/+GkHHUmAuOzgEHUwXvvWSXbY=)";

        decryptPropertyMojo.setEncryptedValue(valueToDecrypt);
        decryptPropertyMojo.execute();

        verify(log).info(contains(expectedDecryptedValue));
    }
}