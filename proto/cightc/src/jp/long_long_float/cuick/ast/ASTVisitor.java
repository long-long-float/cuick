package jp.long_long_float.cuick.ast;

import java.lang.reflect.InvocationTargetException;

import jp.long_long_float.cuick.entity.Entity;
import jp.long_long_float.cuick.utility.ErrorHandler;

public abstract class ASTVisitor<S, E> {

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
    
    public E visit(Node node) {
        String nodeName = node.getClass().getSimpleName();
        try {
            return (E) getClass().getMethod("visit", node.getClass()).invoke(this, node);
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

    public E visit(Entity entity) {
        String nodeName = entity.getClass().getSimpleName();
        try {
            return (E) getClass().getMethod("visit", entity.getClass()).invoke(this, entity);
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
