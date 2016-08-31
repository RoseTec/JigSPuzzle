package jigspuzzle.testutils.factories;

import java.lang.reflect.Field;

/**
 * A class for abstracting from the creation of objects. Instead of calling a
 * construcor in a test, one can let this class create an object.
 *
 * The attributes of the object are initially filled with default values and can
 * also be modified by calling the method
 * <code>withAttribut(String, Object)</code>.
 *
 * Finally to get the object, one has to call the <code>create()</code>-method.
 *
 * The following snippet show an example of creating on instance of class
 * 'MyClass':
 * <pre><code>
 * MyClass instance = (MyClass) FactorySlave.build(MyClass.class).withAttribut("myVar", 42).create();
 * </code></pre> First, one has to tell FactorySlave, what class it should
 * create. It is done by <code>build(MyClass.class)<code>. Then we modify the
 * attribute 'myVar' and assign the value 42 to it. This can be done with
 * arbitrary attributes and values.<br>
 * Finally we get the actual created instance by calling <code>create()</code>.
 *
 * @author RoseTec
 * @see FactorySlave#build(java.lang.Class)
 */
public class FactorySlave {

    /**
     * Builds a new Instance from the given class with default attributes as
     * defined in a separate factory.
     *
     * A factory is given in this package with the name of the given class and
     * adding 'Factory' to the end. E.g: the factory for
     * <code>build(MyClass.class)</code> has the name MyClassFactory.
     *
     * @param classToBuild
     * @return
     * @throws ClassNotFoundException
     */
    public static FactorySlave build(Class<?> classToBuild) throws ClassNotFoundException {
        // find factory for this class
        Class<?> factoryClass;

        try {
            factoryClass = Class.forName(FactorySlave.class.getPackage().getName() + "." + classToBuild.getSimpleName() + "Factory");
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Cannot find the factory for class: " + classToBuild.toString());
        }

        // instanciate factory
        AbstractFactory factoryInstance;

        try {
            factoryInstance = (AbstractFactory) factoryClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new ClassNotFoundException("Cannot create an instance of the factory " + factoryClass.toString());
        }

        //let factory create an object
        Object createdObject;

        try {
            createdObject = factoryInstance.createObject();
        } catch (Exception ex) {
            throw new ClassNotFoundException("The factory could not create an instance", ex);
        }

        return new FactorySlave(classToBuild, createdObject);
    }

    /**
     * The class, that this factory should build.
     */
    private Class<?> classToBuild;

    /**
     * The object, that is created within this factory slave.
     */
    private Object createdObject;

    private FactorySlave(Class<?> classToBuild, Object createdObject) {
        this.classToBuild = classToBuild;
        this.createdObject = createdObject;
    }

    /**
     * Creates the object and returns it.
     *
     * @return
     */
    public Object create() {
        return createdObject;
    }

    /**
     * Sets the given attribute to the given value in the created object.
     *
     * @param attribute
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public FactorySlave withAttribut(String attribute, Object value) throws NoSuchFieldException {
        Class<?> clazz = classToBuild;
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(attribute);
                field.setAccessible(true);
                field.set(createdObject, value);
                return this;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        // no attribute found
        throw new NoSuchFieldException();
    }

}
