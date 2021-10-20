package dev.haeusler.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;

@Mojo(name = "encrypt", defaultPhase = LifecyclePhase.NONE, requiresProject = false)
public class EncryptPropertyMojo extends AbstractJasyptMojo {

    /**
     * Sets the password to be used for decryption.
     */
    @Parameter(required = true, defaultValue = "${jasyptEncryptorPassword}")
    private String jasyptEncryptorPassword;

    /**
     * The decrypted value to be encrypted.
     */
    @Parameter(required = true, defaultValue = "${decryptedValue}")
    private String decryptedValue;

    @Override
    public void execute() {
        final PooledPBEStringEncryptor encryptor = buildEncryptor();
        encryptor.setPassword(jasyptEncryptorPassword);

        final String encryptedValue = PropertyValueEncryptionUtils.encrypt(decryptedValue, encryptor);
        getLog().info("\n" + encryptedValue);
    }

    /**
     * Sets the password to be used for decryption.
     *
     * @param jasyptEncryptorPassword the password to be used.
     */
    public void setJasyptEncryptorPassword(String jasyptEncryptorPassword) {
        this.jasyptEncryptorPassword = jasyptEncryptorPassword;
    }

    /**
     * The decrypted value to be encrypted.
     *
     * @param decryptedValue the value to be encrypted.
     */
    public void setDecryptedValue(String decryptedValue) {
        this.decryptedValue = decryptedValue;
    }
}
