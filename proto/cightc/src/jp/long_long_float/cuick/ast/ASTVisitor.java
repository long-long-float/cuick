package jp.long_long_float.cuick.ast;

import java.lang.reflect.InvocationTargetException;

import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.utility.ErrorHandler;

public abstract class ASTVisitor<T> {

    protected final ErrorHandler errorHandler;
    
    public ASTVisitor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    protected void error(Location location, String message) {
        errorHandler.error(location, message);
    }
    
    protected void warn(Location location, String message) {
        errorHandler.warn(location, message);
    }
    
    public T visit(Node node) {
        try {
            return (T) getClass().getMethod("visit", node.getClass()).invoke(this, node);
        } catch (IllegalAccessException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new Error(e.getCause());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        } catch (SecurityException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return null;
    }

    public T visit(Entity entity) {
        try {
            return (T) getClass().getMethod("visit", entity.getClass()).invoke(this, entity);
        } catch (IllegalAccessException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new Error(e.getCause());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        } catch (SecurityException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return null;
    }

}
