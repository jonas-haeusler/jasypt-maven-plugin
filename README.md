# Jasypt Maven Plugin
The Jasypt Maven Plugin is here to make life a little easier when dealing with encrypted property files.
It provides the `jasypt:read-project-properties` goal to read properties from files and URLs and store the properties as
project properties. Encrypted properties will be automatically decrypted. 
A value is considered "encrypted" when it appears surrounded by ENC(...).

If you have a properties file called `teams.properties` with this content:
```properties
jasypt.encryptor.password=passw0rd

toronto=raptors
miami=ENC(07CZRi17+i1GAwJyfWlZMoinjLkKLVClXeD8bM0NXF2pPYaTRzd16wOLaBf04zMH)
```

and invoke the `jasypt:read-project-properties` goal, it would be the same as declaring the following in your pom.xml:

```xml
<properties>
    <toronto>raptors</toronto>
    <miami>heat</miami>
</properties>
```

The `jasypt:read-project-properties` goal is based on the goal of the same name from the [Properties Maven Plugin](https://www.mojohaus.org/properties-maven-plugin/).

## Goals
- `jasypt:read-project-properties` Reads property files or properties from URLs as Project properties.
- `jasypt:encrypt` Encrypt a single property and print it.
- `jasypt:decrypt` Decrypt a single property and print it.

## Usage
### jasypt:read-project-properties
The `jasypt:read-project-properties` goal reads property files or URLs and stores them as project properties.

```xml
<project>
  <build>
    <plugins>
      <plugin>
        <groupId>dev.haeusler</groupId>
        <artifactId>jasypt-maven-plugin</artifactId>
        <version>1.0</version>
        <executions>
          <execution>
            <phase>initialize</phase>
            <goals>
              <goal>read-project-properties</goal>
            </goals>
            <configuration>
              <files>
                <file>etc/config/dev.properties</file>
              </files>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```
or alternatively
```xml
<project>
  <build>
    <plugins>
      <plugin>
        <groupId>dev.haeusler</groupId>
        <artifactId>jasypt-maven-plugin</artifactId>
        <version>1.0</version>
        <executions>
          <execution>
            <phase>initialize</phase>
            <goals>
              <goal>read-project-properties</goal>
            </goals>
            <configuration>
              <urls>
                <url>classpath:/config/dev.properties</url>
                <url>file:///${env.HOME}/mydev.properties</file>
              </urls>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

You can also modify the encryptor configuration.
```xml
<configuration>
    ...
    <algorithm>PBEWithHmacSHA512AndAES_256</algorithm>
    <poolSize>1</poolSize>
    <keyObtentionIteration>100000</keyObtentionIteration>
    <saltGeneratorClassName>org.jasypt.salt.RandomSaltGenerator</saltGeneratorClassName>
    <ivGeneratorClassName>org.jasypt.iv.RandomIvGenerator</ivGeneratorClassName>
    <stringOutputType>base64</stringOutputType>
</configuration>
```

The default values can be found [here](https://github.com/jonas-haeusler/jasypt-maven-plugin/blob/20b5c1bd2d93040e50dbe606c450592fece0ecba/src/main/java/dev/haeusler/mojo/AbstractJasyptMojo.java#L10-L58).

### jasypt:encrypt
```shell
$ mvn jasypt:encrypt -DjasyptEncryptorPassword="passw0rd" -DdecryptedValue="my-secret"

ENC(eQ7ox25GWN4bO4Q4oMfyXnk6Y1VZjMuq/k4bEByOjXsUT8nXUE03zHwlnUEgBGNh)
```

or as standalone (without a Maven project):

```shell
$ mvn dev.haeusler:jasypt-maven-plugin:1.0:encrypt -DjasyptEncryptorPassword="passw0rd" -DdecryptedValue="my-secret"

ENC(BOHWcke3H8avQefTzQLFtpIR2lYYhL5M2Ohm/1ZQo3I6VddX6Ie8OjicBIcSjLIT)
```

### jasypt:decrypt
```shell
$ mvn jasypt:decrypt -DjasyptEncryptorPassword="passw0rd" -DencryptedValue="ENC(eQ7ox25GWN4bO4Q4oMfyXnk6Y1VZjMuq/k4bEByOjXsUT8nXUE03zHwlnUEgBGNh)"

my-secret
```

or as standalone (without a Maven project):

```shell
$ mvn dev.haeusler:jasypt-maven-plugin:1.0:decrypt -DjasyptEncryptorPassword="passw0rd" -DencryptedValue="ENC(BOHWcke3H8avQefTzQLFtpIR2lYYhL5M2Ohm/1ZQo3I6VddX6Ie8OjicBIcSjLIT)"

my-secret
```
