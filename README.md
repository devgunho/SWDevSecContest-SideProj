# 2019 소프트웨어 개발보안 경진대회 준비 (Testbed)

대회 : https://github.com/gunh0/sw-secure-coding-contest

<br/>

### API Test

**[서울시 Barrier Free Info Link](http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-13441&srvType=S&serviceKind=1&currentPageNo=)**

![image](https://user-images.githubusercontent.com/41619898/62830754-cfb92880-bc4e-11e9-8f42-a848643b8eb3.png)

<br/>

### Json 형식으로 데이터 전처리

```
BFDataSample{
	"businessName" : "혜화문",
	"tel" : "02-731-0320",
	"fax" : "",
	"address" : "서울시 종로구 창경궁로 307",
	"opTime" : "",
	"closedDay" : "",
	"basicInfo" : "서울성곽길 한성대역 근처에 위치한 관광지 옆 공원을 통해 접근 가능",
	"category" : "null",
	"latitude" : "37.58791",
	"longitude" : "127.00347"
}
```

<br/>

### Note

**Using PreparedStatement (not Statemet)**

-   Statemet 취약점을 가지고 있기 때문에 사용하지 않는다.

-   대신 객체를 객체를 캐시에 담아 재사용하는 PreparedStatement를 사용한다.
