==================
glass_stock_alert
==================

This Android service runs on Google Appspot Engine which scraps new RSS feed from CNBC News, organizes feed
to fit the Glass form factor, and sends notifications to client via the Glass Mirror API:

Server: Google AppEngine Service (requiring Java 1.7+)
* listens for feed update activitities (async notifications via REST GET) 
* communicates with client (sending client notificaitons via Glass Mirror API)

This project uses two publicly available APIs:

* Google Glass API 16-18

Client: Google Glass
NB: there's minimal client installation involved other than going through the standard OAuthing dance such as 

http://cnbc-news-alerts.appspot.com/

to ensure that client is equipped with the News appesults 

![](https://github.com/jsu800/glass_stock_alert/blob/master/image/cnbc%20news%20alert%20app%20page.png)