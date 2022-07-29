# Gain Access To Some Dashboards

---

Medium | web | 50 points

---

### Description

Look on the site and try to find services, there must be a dashboard to control the area

---

### First Impressions

We are given a random URL [https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/](https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/)  which takes us to a very nice-looking webpage. None of the buttons seem to do anything so instead, I investigated the source code. Inside the source code is a link to the dashboard! 

<img width="536" alt="website" src="https://user-images.githubusercontent.com/74945479/181757797-e4227204-e4cf-44e2-b230-afc72ca896ee.png">


Our link is:

```php
 [https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org](https://1825d1f6964055e82bb5eda70bbc148d.challenge.hackazon.org/)/8efwygs6p3gu7zmifcq0
```

This directs us to a login page for “Grafana” where a quick search of the docs reveals the default login of admin:admin which happens to work here. 

<img width="536" alt="dashboard" src="https://user-images.githubusercontent.com/74945479/181757847-c87893a4-8a7d-47e3-b3c7-dcf80bba8ee6.png">


We are greeted with this dashboard and clicking the magnifying glass on the left allows us to switch to a different dashboard called “sample” which contains our first flag. 

<img width="537" alt="flag" src="https://user-images.githubusercontent.com/74945479/181757870-56235f7c-8139-4e3d-b3af-9413e95c07d7.png">


# Data Analysis

---

Medium | web | 75 points

---

### Description

Can you find and analyze the data for us? we need information, a code, something…

---

Switching back to our “system” dashboard, we find that the explore button takes us to a terminal where we can type commands to access a database. The buckets command reveals the following buckets (tables)

<img width="534" alt="buckets" src="https://user-images.githubusercontent.com/74945479/181757898-499af62c-90c9-4eeb-b011-d33f06c4aca7.png">


Querying the flag bucket gives us our second flag 

<img width="534" alt="flag2" src="https://user-images.githubusercontent.com/74945479/181757930-4ed83b49-3137-4e75-874e-71c39b3a1427.png">


# Services Intrusion

---

Medium | web | 175 points

---

### Description

there must be a way to retrieve sensitive AI data for the dashboard, find a way to break one service and get the flag hidden in the system

---

Unfortunately, I had no idea what to do with this part of the challenge and I have not solved it :(
