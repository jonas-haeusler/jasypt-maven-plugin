package dev.haeusler.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;

@Mojo(name = "decrypt", defaultPhase = LifecyclePhase.NONE, requiresProject = false)
public class DecryptPropertyMojo extends AbstractJasyptMojo {

    /**
     * Sets the password to be used for decryption.
     */
    @Parameter(required = true, defaultValue = "${jasyptEncryptorPassword}")
    protected String jasyptEncryptorPassword;

    /**
     * The encrypted value to be decrypted.
     */
    @Parameter(required = true, defaultValue = "${encryptedValue}")
    protected String encryptedValue;

    @Override
    public void execute() {
        final PooledPBEStringEncryptor encryptor = buildEncryptor();
        encryptor.setPassword(jasyptEncryptorPassword);

        final String decryptedValue = PropertyValueEncryptionUtils.decrypt(encryptedValue, encryptor);
        getLog().info("\n" + decryptedValue);
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
     * The encrypted value to be decrypted.
     *
     * @param encryptedValue the value to be decrypted.
     */
    public void setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
    }
}
