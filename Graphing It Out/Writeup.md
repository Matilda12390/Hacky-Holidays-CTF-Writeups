# Gain Access To Some Dashboards

---

Medium | web | 50 points

---

### Description

Look on the site and try to find services, there must be a dashboard to control the area

---

### First Impressions

We are given a random URL [https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/](https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/)  which takes us to a very nice-looking webpage. None of the buttons seem to do anything so instead, I investigated the source code. Inside the source code is a link to the dashboard! 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ead859dc-c912-4512-950e-4c517172db60/Untitled.png)

Our link is:

```php
 [https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org](https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/)/8efwygs6p3gu7zmifcq0
```

This directs us to a login page for “Grafana” where a quick search of the docs reveals the default login of admin:admin which happens to work here. 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/875ac48f-10f5-4b06-ad0d-745ddcda6306/Untitled.png)

We are greeted with this dashboard and clicking the magnifying glass on the left allows us to switch to a different dashboard called “sample” which contains our first flag. 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ab2cce5b-7a19-404f-9ad4-cbdd66239ad4/Untitled.png)

# Data Analysis

---

Medium | web | 75 points

---

### Description

Can you find and analyze the data for us? we need information, a code, something…

---

Switching back to our “system” dashboard, we find that the explore button takes us to a terminal where we can type commands to access a database. The buckets command reveals the following buckets (tables)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6f82f54a-e125-4366-9aee-8bd5aa86ee91/Untitled.png)

Querying the flag bucket gives us our second flag 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c989a99a-9213-4b2d-85de-4549e15bf156/Untitled.png)

# Services Intrusion

---

Medium | web | 175 points

---

### Description

there must be a way to retrieve sensitive AI data for the dashboard, find a way to break one service and get the flag hidden in the system

---

Unfortunately, I had no idea what to do with this part of the challenge and I have not solved it :(
