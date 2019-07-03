package com.eappcat.document.server;

public interface DocumentEditStatus {
    int NOT_FOUND=0;
    int READY_TO_EDIT=1;
    int READY_TO_SAVE=2;
    int SAVE_ERROR=3;
    int CLOSE_WITHOUT_CHANGE=4;
    int FORCE_SAVED=6;
    int FORCE_SAVE_ERROR=7;
}
