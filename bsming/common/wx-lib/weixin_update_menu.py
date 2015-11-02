#!/usr/bin/env python
# -*- coding: utf-8 -*-
# filename: weixin_update_menu.py.py
import urllib
import urllib2
import json
import httplib2
import sys
reload(sys)
sys.setdefaultencoding('utf-8')


appid = 'wx78f0898c4ecdb61b'

secret = '0ab7505b7300507f11753680f38a93ea'

menu = {'button':[{'type':'click','name':'我的持仓','key':'EVENT_PORTFOLIO'},{'type':'click','name':'最新资讯','key':'EVENT_NEWS'},{'name':'菜单','sub_button':[{'type':'click','name':'绑定','key':'EVENT_BIND'},{'type':'click','name':'应用下载','key':'EVENT_DOWNLOAD_APP'}]}]}

tokenUrl = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' + appid +'&secret=' + secret

response = urllib2.urlopen(tokenUrl)

tokenHtml = response.read()

print(tokenHtml)

python_object = json.loads(tokenHtml)

accessToken = python_object['access_token']

print('access_token:' + accessToken)

menuPostString = json.dumps(menu,ensure_ascii=False)
print('post_menu:' + menuPostString)

menuPostUrl = 'https://api.weixin.qq.com/cgi-bin/menu/create?access_token='+accessToken

#method 1
menuReq = urllib2.Request(menuPostUrl,menuPostString)
menuResponse = urllib2.urlopen(menuReq)
menuHtml = menuResponse.read()
print('menu update result :'+menuHtml)

#method 2
#conn = httplib2.Http()
#resp, content = conn.request(uri=menuPostUrl,method='POST',headers={'Content-Type': 'application/json; charset=UTF-8'}, body=menuPostString)
#print(content)

