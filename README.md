文档服务器
--------------

文档服务器可以通过upload API上传文件，然后通过调用在线编辑器或者文档api来实现在线预览编辑功能

## 编译

```bash
cd docker
sh build.sh
```

## 启动镜像
```bash
docker-compose up -d
```

## 配置host访问
```text
192.168.154.138 onlyoffice 
192.168.154.138 document-server 
```

## 文档上传api
```http request
POST http://hostname:port/document/callback/{documentId}
Content-Type: multipart/form-data
```
参数 file: 用于指定上传的文件

```json
{
    "document": {
        "id": 5,
        "docId": "9be60e0900134caa81c31fc92b8242cd",
        "name": "file",
        "type": "xlsx",
        "hash": "0f5fd7c2eeb37ad5d9c7a1d473816665",
        "path": null,
        "contentType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "createdDate": 1562131607795,
        "updatedDate": 1562131607795,
        "version": 1
    },
    "status": 0
}
```

## 编辑器

### 直接调用

```http request
http://document-server:8080/document/editor/{documentId}
```
编辑器会通过html来渲染出编辑器

### 前后端分离的可以调用

```http request
GET http://document-server:8080/document/{documentId}
```
响应
```json
{
    "documentType": "spreadsheet",
    "document": {
        "id": 5,
        "docId": "9be60e0900134caa81c31fc92b8242cd",
        "name": "file",
        "type": "xlsx",
        "hash": "0f5fd7c2eeb37ad5d9c7a1d473816665",
        "path": null,
        "contentType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "createdDate": 1562131608000,
        "updatedDate": 1562131608000,
        "version": 1
    },
    "error": 0,
    "config": {
        "localDir": "/data/documents",
        "documentServerRootUrl": "http://onlyoffice",
        "serverUrl": "http://document-server:8080",
        "documentServerUrl": "http://onlyoffice/web-apps/apps/api/documents/api.js",
        "documentConverterServiceUrl": "http://onlyoffice/ConvertService.ashx"
    }
}
```


### 编辑器回调地址

##### 文档回调地址
请求
```http request
GET http://document-server:8080/document/stream/{documentId}
```

##### 文档保存回调地址
请求
```http request
GET http://document-server:8080/document/callback/{documentId}
```


## 文档转换

### 转换请求

```http request
POST http://document-server:8080/converter/request}

{
    "async": true,
    "documentId": "f0af8cf7f9ea47e18c1a602648467867",
    "convertTo": "pdf"
}
```
响应
```json
{
    "result": {
        "endConvert": "True",
        "fileUrl": "http://onlyoffice/cache/files/conv_3333_pdf/output.pdf/file.pdf?md5=Cl7a58616fSz1qDUiATEZw&expires=1562133209&disposition=attachment&ooname=output.pdf",
        "percent": 100
    },
    "request": {
        "id": 2,
        "requestDate": 1562132413471,
        "parameters": "{\"async\":true,\"fileType\":\"xlsx\",\"key\":\"3333\",\"outputtype\":\"pdf\",\"title\":\"file\",\"url\":\"http://192.168.7.166:8080/document/stream/f0af8cf7f9ea47e18c1a602648467867\"}",
        "requestNo": "160ae92a32b14f418ef88ce309adf5f3",
        "createdDate": 1562132413422,
        "updatedDate": 1562132413422,
        "version": 0
    },
    "error": 0
}
```

### 状态检查
```http request
GET http://document-server:8080/converter/{requestNo}}
```
响应
```json
{
    "result": {
        "endConvert": "True",
        "fileUrl": "http://onlyoffice/cache/files/conv_3333_pdf/output.pdf/file.pdf?md5=Cl7a58616fSz1qDUiATEZw&expires=1562133209&disposition=attachment&ooname=output.pdf",
        "percent": 100
    },
    "request": {
        "id": 2,
        "requestDate": 1562132413471,
        "parameters": "{\"async\":true,\"fileType\":\"xlsx\",\"key\":\"3333\",\"outputtype\":\"pdf\",\"title\":\"file\",\"url\":\"http://192.168.7.166:8080/document/stream/f0af8cf7f9ea47e18c1a602648467867\"}",
        "requestNo": "160ae92a32b14f418ef88ce309adf5f3",
        "createdDate": 1562132413422,
        "updatedDate": 1562132413422,
        "version": 0
    },
    "error": 0
}
```