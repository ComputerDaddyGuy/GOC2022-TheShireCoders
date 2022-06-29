# GOC2022-TheShireCoders

Welcome on our source repository.

Our solution is named **Poseidon** and is targeted to be used by companies to protect their employees and their customers.

It is composed of multiple applications:
* `rules-aggregator-api`: connects to several Spam/Phishing data providers (SpamBee, IBM Trusteer, etc.) and compiles everything to get a single and simple json file, containing some rules. 
* `mail-analyzer-cli`: takes the rules json file as input, and a list of *.eml files, to analyze them. Matching emails are considered as phishing and their details are inserted into threat database.
* `threat-response-api`: connects to threat database and reacts immediately to take appropriate actions: block phishing websites, send a SMS to alert, post a Twitt, etc.
* `threat-response-web`: allow to visualize in real-time the content of the threat database
* `traffic-faker`: an application to flood phishing websites with fake accounts until the threat response api manages to block entreprise network towarding this website.
