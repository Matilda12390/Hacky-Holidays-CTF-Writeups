# Checking The Location Database

---

Medium | PPC | 50 Points 

---

### Description

Can you connect to the vehicle database of our city?

---

### First Impressions

We are given the following link: tcp://portal.hackazon.org:17008

So time for some investigation on that address. 

### NMAP output

```php
└─$ nmap portal.hackazon.org -p17008 -sV -sC
Starting Nmap 7.92 ( https://nmap.org ) at 2022-07-12 02:44 EDT
Nmap scan report for portal.hackazon.org (136.243.68.77)
Host is up (0.30s latency).
rDNS record for 136.243.68.77: static.77.68.243.136.clients.your-server.de

PORT      STATE SERVICE VERSION
17008/tcp open  redis   Redis key-value store

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 13.91 seconds
```

We find a redis store. I have no idea what a redis store is so to HackTricks! [Information here](https://book.hacktricks.xyz/network-services-pentesting/6379-pentesting-redis).

So lets get some info from this redis store. 

```php
└─$ nc -vn 136.243.68.77 17008      
(UNKNOWN) [136.243.68.77] 17008 (?) open
info

```

We see the following in the info

```bash
# Keyspace
db0:keys=1002,expires=0,avg_ttl=0
```

According to hacktricks, this means we have one database inside. We can connect with SELECT 0 and view all keys with KEYS *. I won’t copy all the keys here since there are 1002 of them but a search through the results unveils a _flag key! Very suspicious. And sure enough, if we GET that key, we get our flag for the first part of this challenge!

```php
GET _flag
$25
CTF{DGErbbodqEeHQhjeDs8g}
```
