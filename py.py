# -*- coding: utf-8 -*-
"""
Created on Wed Mar 20 18:11:50 2024

@author: mengying
"""
import sys
import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
# def add():
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
# 指定GeckoDriver路径
# driver_path = r"F:\geckodriver\geckodriver.exe"  # 将"path_to_your_geckodriver"替换为你的GeckoDriver路径
# driver = webdriver.Firefox(executable_path=driver_path)
driver = webdriver.Firefox()
# #input_box = driver.find_element(By.CSS_SELECTOR,"search-card-input") #找到输入框
# driver.get("https://www.zaiwen.top/chat/aidraw")

# # 滚动到页面底部
# #driver.execute_script("window.scrollBy(0, document.body.scrollHeight)")

# # 找到输入框
# #input_box = driver.find_element(By.CSS_SELECTOR, "input.nav-search-input")
# #input_box.send_keys("hello") #输入


# time.sleep(3)

# # 通过CSS选择器定位按钮元素
# search_button = driver.find_element_by_css_selector("#txc-change-log-pc__close")
# # 点击按钮
# search_button.click()

# time.sleep(3)
# # 找到输入框
# input_box = driver.find_element(By.CSS_SELECTOR, ".arco-textarea-scroll > textarea:nth-child(2)")
# input_box.send_keys("hello") #输入

# search_button = driver.find_element_by_css_selector("button.arco-btn-long:nth-child(2)")
# search_button.click()
# #driver.quit()

# 打开网页
driver.get("http://172.25.47.157:7860/?__theme=dark")   # 替换为您要操作的网页URL
time.sleep(10)

# 执行JavaScript代码，下滑页面
# driver.execute_script("window.scrollTo(0, document.body.scrollHeight * 1 / 2);")
# time.sleep(10)

# 定位文本框元素
lab = driver.find_element(By.ID, 'script_txt2img_aiv20_668311397ai_PTX')
text_box = lab.find_element(By.CSS_SELECTOR, "textarea")


lab_huafeng = driver.find_element(By.ID, 'script_txt2img_aiv20_668311397ai_CX4')
text_huafeng = lab_huafeng.find_element(By.CSS_SELECTOR, "textarea")

lab_shidai = driver.find_element(By.ID, 'script_txt2img_aiv20_668311397ai_style')
text_shidai = lab_shidai.find_element(By.CSS_SELECTOR, "textarea")

# 清空文本框内容（可选）
text_box.clear()

# 输入文本
# text_box.send_keys("Rescue mission, intense fire, brave firefighter, burning building\n"
#                   "Emergency response, trapped victims, smoke-filled room, urgent evacuation")
input_text = ' '.join(sys.argv[1:])
# text_box.send_keys("")
instring=input_text.split('xxxxxxxxxxxxxx')
text_huafeng.send_keys(instring[2])
text_shidai.send_keys(instring[1])

for line in instring[0].split('\n'):
    text_box.send_keys(line)
    text_box.send_keys('\n')  # 添加回车符
# text_box.send_keys(input_text)

# 文件方式
# with open('D:\\aWEB\\alibaba-demo\\output.txt', 'r') as file:
#     content = file.read()
#     print(content)
#     for line in content.split('\n'):
#         text_box.send_keys(line)
#         text_box.send_keys('\n')  # 添加回车符

# 等待生成按钮可点击
# generate_button = WebDriverWait(driver, 10).until(
#     EC.element_to_be_clickable((By.ID, 'txt2img_generate'))
# )
# # 点击生成按钮
# generate_button.click()




generate_button = WebDriverWait(driver, 10).until(
    EC.element_to_be_clickable((By.ID, 'txt2img_generate'))
)
# 点击生成按钮
generate_button.click()
# 等待一段时间，确保生成完成
time.sleep(180)

# 保存页面截图
driver.save_screenshot('D:\图片\screenshot.png')

# 关闭浏览器
# driver.quit()



# import sys

# def main():
#     if len(sys.argv) != 2:
#         print("Usage: python script.py <filename>")
#         return

#     filename = sys.argv[1]
#     try:
#         with open(filename, 'r') as file:
#             content = file.read()
#             print("Content read from file:", content)
#             # 在这里可以继续对content进行处理
#     except FileNotFoundError:
#         print("File not found:", filename)

# if __name__ == "__main__":
#     main()
