## PIZZA PAZZI 1

---

Medium | Reversing, Android | 75 Points

---

### Description

Listening in on the conversation

---

### First Impressions

This part of the challenge was initially broken when I attempted the challenge so I have moved this section up to its proper place. 

We are given an APK file. Going to google, we see that there are two methods to tackle APK files. First we can simply extract the files from the apk. We use 

```bash
apktool d pizza-pazzi.apk
```

to do so. Inside, we see a bunch of .smali files but they aren’t terribly readable. So lets see if the easy approach works, if we recursively grep the folder looking for CTF we don’t find any matches (didn’t think that would work) but I decided to try searching the word “hack” wondering if anything from the events hackazon branding would show up and sure enough, we get a URL [http://pizzapazzi.challenge.hackazon.org](http://pizzapazzi.challenge.hackazon.org) which leads us to a nginx landing page which gives us our first flag! CTF{St4RT_Y0uR_3NG1N3X}. 

## PIZZA PAZZI 2 & 3 & 4

---

Medium | Reversing, Android | 75 points total

---

### Description

2: Strange things are happening.…

3: Breaking the chain

4: Whoami?

---

While searching for CTF directly didn’t work, I got the idea from a previous challenge that the flag could be in base64 text instead. I didn’t expect this to work but sure enough, searching for Q1R returns 3 base64 strings!

```bash
└─$ grep -r "Q1R"   
smali_classes3/com/fooddeliveryapp/Model/Call.smali:    const-string v2, "Q1RGe1doMF80bV9JfQ=="
smali_classes3/com/fooddeliveryapp/Model/Call.smali:    const-string v3, "Q1RGe1doMF80bV9JfQ=="
smali_classes3/com/fooddeliveryapp/Model/Call.smali:    const-string v1, "Q1RGe1doNHRfMV80bV93MHJ0aH0="
smali_classes3/com/fooddeliveryapp/Model/Call.smali:    const-string v2, "Q1RGe1doNHRfMV80bV93MHJ0aH0="
smali_classes4/com/fooddeliveryapp/Activities/LoginActivity.smali:    const-string v1, "Q1RGe1doM3JlXzFzX3RoM19mMDBkfQ=="
smali_classes4/com/fooddeliveryapp/Activities/LoginActivity.smali:    const-string v2, "Q1RGe1doM3JlXzFzX3RoM19mMDBkfQ=="
```

The first one decodes to CTF{Wh0_4m_I} which is the flag for 2.

The second one decodes to CTF{Wh4t_1_4m_w0rth} which is the flag for 3. 

And the final one decodes to CTF{Wh3re_1s_th3_f00d} which is the flag for 4.

Too easy!
