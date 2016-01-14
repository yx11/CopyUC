package com.yx.android.copyuc.ui.adapter;

import com.yx.android.copyuc.ui.holder.ViewHolderBase;
import com.yx.android.copyuc.ui.impl.ViewHolderCreator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Created by yx on 2016/1/14 0014.
 */
public class LazyViewHolderCreator<T> implements ViewHolderCreator<T> {
    private final Constructor<?> mConstructor;
    private Object[] mInstanceObjects;

    private LazyViewHolderCreator(Constructor<?> constructor, Object[] instanceObjects) {
        this.mConstructor = constructor;
        this.mInstanceObjects = instanceObjects;
    }

    public static <ItemDataType> ViewHolderCreator<ItemDataType> create(Object enclosingInstance, Class<?> cls, Object... args) {
        if(cls == null) {
            throw new IllegalArgumentException("ViewHolderClass is null.");
        } else {
            boolean isEnclosingInstanceClass = false;
            if(cls.getEnclosingClass() != null && !Modifier.isStatic(cls.getModifiers())) {
                isEnclosingInstanceClass = true;
            }

            int argsLen = isEnclosingInstanceClass?args.length + 1:args.length;
            Object[] instanceObjects = new Object[argsLen];
            byte copyStart = 0;
            if(isEnclosingInstanceClass) {
                instanceObjects[0] = enclosingInstance;
                copyStart = 1;
            }

            if(args.length > 0) {
                System.arraycopy(args, 0, instanceObjects, copyStart, args.length);
            }

            Class[] parameterTypes = new Class[argsLen];

            for(int constructor = 0; constructor < instanceObjects.length; ++constructor) {
                parameterTypes[constructor] = instanceObjects[constructor].getClass();
            }

            Constructor var11 = null;

            try {
                var11 = cls.getDeclaredConstructor(parameterTypes);
            } catch (NoSuchMethodException var10) {
                var10.printStackTrace();
            }

            if(var11 == null) {
                throw new IllegalArgumentException("ViewHolderClass can not be initiated");
            } else {
                LazyViewHolderCreator lazyCreator = new LazyViewHolderCreator(var11, instanceObjects);
                return lazyCreator;
            }
        }
    }

    public ViewHolderBase<T> createViewHolder(int position) {
        Object object = null;

        try {
            boolean ex = this.mConstructor.isAccessible();
            if(!ex) {
                this.mConstructor.setAccessible(true);
            }

            object = this.mConstructor.newInstance(this.mInstanceObjects);
            if(!ex) {
                this.mConstructor.setAccessible(false);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        if(object != null && object instanceof ViewHolderBase) {
            return (ViewHolderBase)object;
        } else {
            throw new IllegalArgumentException("ViewHolderClass can not be initiated");
        }
    }
}
