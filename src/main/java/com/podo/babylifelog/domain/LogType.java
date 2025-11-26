package com.podo.babylifelog.domain;

/**
 * Value object (enum) representing types of baby life log events.
 */
public enum LogType {
    NAP,            // 낮잠
    SLEEP,          // 밤잠
    FEEDING,        // 수유/이유식
    DIAPER,         // 기저귀
    MILESTONE,      // 발달 이정표 (뒤집기, 걷기 등)
    HEALTH,         // 건강 (체온, 약 등)
    PLAY,           // 놀이
    OTHER           // 기타
}

