/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oms3.gen;

/**
 *
 * @author od
 */
public interface Access {
    void setTarget(Object o);
    Object toObject();
    void pass(Access from);
}
