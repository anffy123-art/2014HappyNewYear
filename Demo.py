import requests  
from bs4 import BeautifulSoup  
  
url = "https://medium.com/?tag=software-engineering"  
headers = {"User-Agent": "Mozilla/5.0"}  
  
response = requests.get(url, headers=headers)  
soup = BeautifulSoup(response.text, "html.parser")  
  

title = soup.find("title").text.strip()  
print("Title:", title)  
  
content = soup.find("div", class_="content").text.strip()  
print("Content:", content)