package com.javaalura.literAlura.services;

public interface IConvierteDatos {
    <T> T convertdata (String json,Class<T> clase);
}
