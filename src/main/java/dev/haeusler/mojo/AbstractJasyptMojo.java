package dev.haeusler.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

abstract class AbstractJasyptMojo extends AbstractMojo {

    /**
     * Sets the algorithm to be used for encryption.
     * <p>
     * This algorithm has to be supported by your JCE provider and, if this provider supports it, you can also
     * specify <i>mode</i> and <i>padding</i> for it, like ALGORITHM/MODE/PADDING.
     */
    @Parameter(defaultValue = "PBEWithHMACSHA512AndAES_256")
    private String algorithm = "PBEWithHMACSHA512AndAES_256";

    /**
     * Sets the size of the pool of encryptors to be created.
     */
    @Parameter(defaultValue = "2")
    private int poolSize = 2;

    /**
     * Sets the number of hashing iterations applied to obtain the encryption key.
     */
    @Parameter(defaultValue = "100000")
    private int keyObtentionIteration = 100_000;

    /**
     * Sets the security provider to be used for obtaining the encryption algorithm.
     * The provider does not have to be registered beforehand, and its use will not result in its being registered.
     */
    @Parameter
    private String providerClassName = null;

    /**
     * Sets the salt generator to be used.
     */
    @Parameter(defaultValue = "org.jasypt.salt.RandomSaltGenerator")
    private String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";

    /**
     * Sets the IV generator to be used.
     */
    @Parameter(defaultValue = "org.jasypt.iv.RandomIvGenerator")
    private String ivGeneratorClassName = "org.jasypt.iv.RandomIvGenerator";

    /**
     * Sets the form in which String output will be encoded. Available encoding types are:
     * <ul>
     *     <li>base64 (default)</li>
     *     <li>hexadecimal</li>
     * </ul>
     */
    @Parameter(defaultValue = "base64")
    private String stringOutputType = "base64";

    protected PooledPBEStringEncryptor buildEncryptor() {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm(algorithm);
        config.setPoolSize(poolSize);
        config.setKeyObtentionIterations(keyObtentionIteration);
        config.setProviderClassName(providerClassName);
        config.setSaltGeneratorClassName(saltGeneratorClassName);
        config.setIvGeneratorClassName(ivGeneratorClassName);
        config.setStringOutputType(stringOutputType);

        encryptor.setConfig(config);

        return encryptor;
    }

    /**
     * Sets the algorithm to be used for encryption.
     * <p>
     * This algorithm has to be supported by your JCE provider and, if this provider supports it, you can also
     * specify <i>mode</i> and <i>padding</i> for it, like ALGORITHM/MODE/PADDING.
     *
     * @param algorithm the name of the algorithm to be used
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Sets the size of the pool of encryptors to be created.
     *
     * @param poolSize the size of the pool to be used if this configuration is used with a pooled encryptor
     */
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    /**
     * Sets the number of hashing iterations applied to obtain the encryption key.
     *
     * @param keyObtentionIteration the number of iterations.
     */
    public void setKeyObtentionIteration(int keyObtentionIteration) {
        this.keyObtentionIteration = keyObtentionIteration;
    }

    /**
     * Sets the security provider to be used for obtaining the encryption algorithm.
     * The provider does not have to be registered beforehand, and its use will not result in its being registered.
     *
     * @param providerClassName the name of the security provider class.
     */
    public void setProviderClassName(String providerClassName) {
        this.providerClassName = providerClassName;
    }

    /**
     * Sets the salt generator to be used.
     *
     * @param saltGeneratorClassName the name of the salt generator class.
     */
    public void setSaltGeneratorClassName(String saltGeneratorClassName) {
        this.saltGeneratorClassName = saltGeneratorClassName;
    }

    /**
     * Sets the IV generator to be used.
     *
     * @param ivGeneratorClassName the name of the IV generator class.
     */
    public void setIvGeneratorClassName(String ivGeneratorClassName) {
        this.ivGeneratorClassName = ivGeneratorClassName;
    }

    /**
     * Sets the form in which String output will be encoded. Available encoding types are:
     * <ul>
     *     <li>base64 (default)</li>
     *     <li>hexadecimal</li>
     * </ul>
     *
     * @param stringOutputType the string output type.
     */
    public void setStringOutputType(String stringOutputType) {
        this.stringOutputType = stringOutputType;
    }
}
