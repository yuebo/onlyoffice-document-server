<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="${config.documentServerUrl?html}"></script>
        <script type="text/javascript">
            function createEditor(){
                window.docEditor = new DocsAPI.DocEditor("editor", {
                    "document": {
                        "fileType": "${document.type?js_string}",
                        "key": "${document.hash?js_string}",
                        "title": "${document.name?js_string}",
                        "url": "${config.serverUrl?js_string}/document/stream/${document.docId?js_string}",
                    },
                    "documentType": "${documentType?js_string}",
                    "editorConfig": {
                        "customization":{
                            "forcesave":true
                        },
                        "callbackUrl": "${config.serverUrl?js_string}/document/callback/${document.docId?js_string}",
                        "mode": "${mode?js_string}",
                        "lang": "zh-CN"
                    },
                    "height": "100%",
                    "width": "100%",
                    // "events": {
                    //     "onAppReady": onAppReady
                    // }
                });
            }
        </script>
        <style>
            * {
                margin:0;
                padding:0;
            }

            html,body{
                height:100%;
            }

        </style>
    </head>
    <body onload="createEditor()">
        <div id="editor"></div>
    </body>
</html>