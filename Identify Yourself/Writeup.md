## CAN YOU GAIN ACCESS TO YOUR DIGITAL ID?

---

Easy | Reversing, Mobile | 100 points

---

### Description

The smart city is implementing a digital identity to keep sensitive data away from the AI. Can you confirm that it is securely implemented?

Can you access your digital ID?

---

### First Impressions

We are given an apk file and a raw file. Apparently the .raw format is used by sony cameras but this file will not open in any image viewer.. strange. If we open it in a text editor, we see the following string that no basic decoder website can identify.

```php
CiMwl2sIlzwtT4Mdm0dmmtOVV79W1dV1kIhWVWJqcYaSZu0ti0aVIkFD6Gim3Uhx:OpFUeq8AsLskv9nSZ1FHYRGvM912ufXYUGI82aiOeX7eFvno9VANOIyH9VXkRkeJYDD74nTLWF22pGsu1G6B4tKGNnjGZ9di1QEIhyDDoxU=
```

hmm. Guess we’ll move onto the apk file. We extract the files with apktool:

```bash
apktool d DigitalID.apk
```

I searched the files with grep for several keywords but all I found was:

```bash
smali_classes3/be/deloitte/digitalID/MainActivity.smali:    const-string v2, "{\'username\':\'test\', \'flag\':\'CTF{redacted}\'}"
```

So it looks like some further reversing is required. Using google, I discovered that we can decompile our apk back into .class java files using the instructions from this link: [https://stackoverflow.com/questions/5582824/decompile-smali-files-on-an-apk](https://stackoverflow.com/questions/5582824/decompile-smali-files-on-an-apk)

So first I used dex2jar to convert our apk file to a jar file:

```bash
git clone https://github.com/pxb1988/dex2jar
./gradlew distZip
cd dex-tools/build/distributions
unzip dex-tools-2.2-SNAPSHOT.zip
sh d2j-dex2jar.sh -f ~/Downloads/HackyHolidays/IdentifyYourself/DigitalID.apk
```

This will create a DigitalID.jar inside dex-tools/build/distributions/dex-tools-2.2-SNAPSHOT

To view this jar file, we use [http://java-decompiler.github.io/](http://java-decompiler.github.io/) which is a nice GUI to view the code.

To build it:

```bash
git clone https://github.com/java-decompiler/jd-gui.git
cd jd-gui
./gradlew build
```

To run the GUI:

```bash
cd ~/…/IdentifyYourself/jd-gui/build/libs
java -jar jd-gui-1.6.6.jar
```

We can then load our DigitalID.jar file inside the GUI and we see a bunch of folders and files we can examine. 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/546c149f-0539-47d2-8b64-c274b42108b4/Untitled.png)

The main files of interest are the files inside be.deloitte.digitalID since those are the app specific files while the rest are from included libraries and default android stuff. 

The majority of files are pretty unremarkable, except for the MainActivity.class file. We can clearly see that previous CTF{redacted} in here

![VirtualBox_Kali-Linux-2022.2-virtualbox-amd64_29_07_2022_21_10_07.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bd80a2c6-9876-42fc-9c5f-f44e2d0c79df/VirtualBox_Kali-Linux-2022.2-virtualbox-amd64_29_07_2022_21_10_07.png)

So reading through the file, I get the impression that what is happening here is

- A user enters their 4 digit pin code
- The program attempts to load their user details by using the users pin code 4 times in a string as an AES decryption key to decrypt the key used to encrypt the user details in the session.raw file. The session.raw file seems to be split into two different strings. The part before the : is the encrypted key used to encrypt the part after the : which is the user details.
- Then the program uses its decrypted key to decrypt the user details inside the session.raw file.

So we must need to recreate the program to achieve this and see what our given session.raw file says. However, first of all we need the pin code. There is no hint to the pin code in the code so we must need to brute force it. Fortunately a 4 digit code should be pretty easy to brute force. 

So I have never coded in Java before so I used the code in this StackOverflow post as a base to build my program on:

[https://stackoverflow.com/questions/5520640/encrypting-and-decrypting-using-java-unable-to-get-same-output/5520786#5520786](https://stackoverflow.com/questions/5520640/encrypting-and-decrypting-using-java-unable-to-get-same-output/5520786#5520786)

My code can be found in the [EncryptDecrypt.java](http://EncryptDecrypt.java) file in this github.

After running my code we get the following result

```bash
└─$ java EncryptDecrypt      
Picked up _JAVA_OPTIONS: -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true
decrypted: ��n�����+r�>����ڡH,��Γ�U��M��*#��5���b�T
�y��aq6.�é W�[�j
          X�A��j�aK���Z~\��R�CA:~`{L�
decrypted: ���*�Ai�o�����ZeA�Ӛ��Z��M�J>�▒▒K�B�n
decrypted: 6f1a9314bab94240b8a06a72158a1e03
decrypted: {'username':'test', 'flag':'CTF{c1e6ae2d5bffb32ac4378d74a3d5bd55}'}
decrypted: ��$N�ƙ�
*��e�4�5����G���O�s
                   �$C�}+��b
decrypted: T▒b�����7�;E#G�=��o���Jq�R� Mb".��)�lǸj�TE1
uU0�:�U�pV �iW�▒=����*@
K��*�H����$��*
decrypted: Q՝�1g�R�1���-P1F;=Z�;s�(��\�Nq�^�J׆��j▒o�
decrypted: jx
             �|�ԇ&%�,SJ�P�A$p���<�������tg��Q   gY�n�+I
Rj��ǳ���Ml�5H������+��^��
decrypted: cD}▒f>���p�
                      !�6[�S���Ⅺ�L4"�6�e{RKY�T����|�
decrypted: ,�Y�6K/&ц2�T)ӄ�b�e�٧���e����=▒������/0
decrypted: ����/�?�"��u�zH�á��s}�i��/L�}ӑ�+$t.>��
decrypted: �
            *�}.��BT
                        �n�(�בtGՅO�ht~�^M(�~�㊢�
decrypted: ���g�t���R%z۳�7��7]#o���t����r@zy������
(�k����Y��Y�T1f▒��U�[RTi��s@W���
decrypted: �
            ��mXڴk2&���yݜj+�Io��|��Ϫ���ge��~,ʘ�����
decrypted: �r����Q
El▒�[���XP�P�����a��p/6D�+z,p���
decrypted: �<O�B�|���S���Q�F���WuW�_o��m�
                                         +���[u��
decrypted: l��(74< (�_+g�V;����o?�B�[���
ў�S���S��                               �:
decrypted: ?��w���G��K�;V ��u�gdyk$g;���]���X{v�%��
decrypted: ��������gf��HZ       Ӈ���v�q�:*R
                                           Ԡ��bY�<^��   ?�J<
decrypted: �p��>Ѐ�)$8���]I���ǁu�����$��FZ��x}'W�Jg�5
n��DӴnr$LQ��y.�g���I!:�h��e��
decrypted: `o��B��Z&/�▒��S"ǆ�M�1�N(�"���y�
                                          &N��~
decrypted: �Xp{�ˏ�p�,�v��k��N�!�li��h:>�j~�-;���
decrypted: ��,�]hd���q�����|4e��▒zB%���s�B������
decrypted: b�{���G{ʚ<���� (G���k�z4B�   ںL�~D�Ac�vlJ
```

As we can see, there were multiple pin codes that were valid AES keys but only one gets us our flag of CTF{c1e6ae2d5bffb32ac4378d74a3d5bd55}.
