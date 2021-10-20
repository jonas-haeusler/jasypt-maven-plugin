package dev.haeusler.mojo;

import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EncryptPropertyMojoTest {

    private static final String jasyptEncryptorPassword = "super_secret_passw0rd";

    private static Log log;

    private static EncryptPropertyMojo encryptPropertyMojo;

    @BeforeAll
    static void setUp() {
        encryptPropertyMojo = new EncryptPropertyMojo();

        // make sure we always have the same encrypted value
        encryptPropertyMojo.setSaltGeneratorClassName("org.jasypt.salt.ZeroSaltGenerator");
        encryptPropertyMojo.setIvGeneratorClassName("dev.haeusler.mojo.FixedStringIvGenerator");

        encryptPropertyMojo.setJasyptEncryptorPassword(jasyptEncryptorPassword);

        log = mock(Log.class);
        encryptPropertyMojo.setLog(log);
    }

    @Test
    public void testEncrypt() {
        final String expectedEncryptedValue = "ENC(jMI5t0UiN2UJ3do70j/+GkHHUmAuOzgEHUwXvvWSXbY=)";
        final String valueToEncrypt = "value-to-encrypt";

        encryptPropertyMojo.setDecryptedValue(valueToEncrypt);
        encryptPropertyMojo.execute();

        verify(log).info(contains(expectedEncryptedValue));
    }
}