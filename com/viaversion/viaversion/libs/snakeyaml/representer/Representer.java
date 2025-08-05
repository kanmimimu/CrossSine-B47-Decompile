package com.viaversion.viaversion.libs.snakeyaml.representer;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.TypeDescription;
import com.viaversion.viaversion.libs.snakeyaml.introspector.Property;
import com.viaversion.viaversion.libs.snakeyaml.introspector.PropertyUtils;
import com.viaversion.viaversion.libs.snakeyaml.nodes.MappingNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeId;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.ScalarNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.SequenceNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Representer extends SafeRepresenter {
   protected Map typeDefinitions = Collections.emptyMap();

   public Representer(DumperOptions options) {
      super(options);
      this.representers.put((Object)null, new RepresentJavaBean());
   }

   public TypeDescription addTypeDescription(TypeDescription td) {
      if (Collections.EMPTY_MAP == this.typeDefinitions) {
         this.typeDefinitions = new HashMap();
      }

      if (td.getTag() != null) {
         this.addClassTag(td.getType(), td.getTag());
      }

      td.setPropertyUtils(this.getPropertyUtils());
      return (TypeDescription)this.typeDefinitions.put(td.getType(), td);
   }

   public void setPropertyUtils(PropertyUtils propertyUtils) {
      super.setPropertyUtils(propertyUtils);

      for(TypeDescription typeDescription : this.typeDefinitions.values()) {
         typeDescription.setPropertyUtils(propertyUtils);
      }

   }

   protected MappingNode representJavaBean(Set properties, Object javaBean) {
      List<NodeTuple> value = new ArrayList(properties.size());
      Tag customTag = (Tag)this.classTags.get(javaBean.getClass());
      Tag tag = customTag != null ? customTag : new Tag(javaBean.getClass());
      MappingNode node = new MappingNode(tag, value, DumperOptions.FlowStyle.AUTO);
      this.representedObjects.put(javaBean, node);
      DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.FLOW;

      for(Property property : properties) {
         Object memberValue = property.get(javaBean);
         Tag customPropertyTag = memberValue == null ? null : (Tag)this.classTags.get(memberValue.getClass());
         NodeTuple tuple = this.representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
         if (tuple != null) {
            if (!((ScalarNode)tuple.getKeyNode()).isPlain()) {
               bestStyle = DumperOptions.FlowStyle.BLOCK;
            }

            Node nodeValue = tuple.getValueNode();
            if (!(nodeValue instanceof ScalarNode) || !((ScalarNode)nodeValue).isPlain()) {
               bestStyle = DumperOptions.FlowStyle.BLOCK;
            }

            value.add(tuple);
         }
      }

      if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
         node.setFlowStyle(this.defaultFlowStyle);
      } else {
         node.setFlowStyle(bestStyle);
      }

      return node;
   }

   protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
      ScalarNode nodeKey = (ScalarNode)this.representData(property.getName());
      boolean hasAlias = this.representedObjects.containsKey(propertyValue);
      Node nodeValue = this.representData(propertyValue);
      if (propertyValue != null && !hasAlias) {
         NodeId nodeId = nodeValue.getNodeId();
         if (customTag == null) {
            if (nodeId == NodeId.scalar) {
               if (property.getType() != Enum.class && propertyValue instanceof Enum) {
                  nodeValue.setTag(Tag.STR);
               }
            } else {
               if (nodeId == NodeId.mapping && property.getType() == propertyValue.getClass() && !(propertyValue instanceof Map) && !nodeValue.getTag().equals(Tag.SET)) {
                  nodeValue.setTag(Tag.MAP);
               }

               this.checkGlobalTag(property, nodeValue, propertyValue);
            }
         }
      }

      return new NodeTuple(nodeKey, nodeValue);
   }

   protected void checkGlobalTag(Property property, Node node, Object object) {
      if (!object.getClass().isArray() || !object.getClass().getComponentType().isPrimitive()) {
         Class<?>[] arguments = property.getActualTypeArguments();
         if (arguments != null) {
            if (node.getNodeId() == NodeId.sequence) {
               Class<? extends Object> t = arguments[0];
               SequenceNode snode = (SequenceNode)node;
               Iterable<Object> memberList = Collections.emptyList();
               if (object.getClass().isArray()) {
                  memberList = Arrays.asList(object);
               } else if (object instanceof Iterable) {
                  memberList = (Iterable)object;
               }

               Iterator<Object> iter = memberList.iterator();
               if (iter.hasNext()) {
                  for(Node childNode : snode.getValue()) {
                     Object member = iter.next();
                     if (member != null && t.equals(member.getClass()) && childNode.getNodeId() == NodeId.mapping) {
                        childNode.setTag(Tag.MAP);
                     }
                  }
               }
            } else if (object instanceof Set) {
               Class<?> t = arguments[0];
               MappingNode mnode = (MappingNode)node;
               Iterator<NodeTuple> iter = mnode.getValue().iterator();

               for(Object member : (Set)object) {
                  NodeTuple tuple = (NodeTuple)iter.next();
                  Node keyNode = tuple.getKeyNode();
                  if (t.equals(member.getClass()) && keyNode.getNodeId() == NodeId.mapping) {
                     keyNode.setTag(Tag.MAP);
                  }
               }
            } else if (object instanceof Map) {
               Class<?> keyType = arguments[0];
               Class<?> valueType = arguments[1];
               MappingNode mnode = (MappingNode)node;

               for(NodeTuple tuple : mnode.getValue()) {
                  this.resetTag(keyType, tuple.getKeyNode());
                  this.resetTag(valueType, tuple.getValueNode());
               }
            }
         }

      }
   }

   private void resetTag(Class type, Node node) {
      Tag tag = node.getTag();
      if (tag.matches(type)) {
         if (Enum.class.isAssignableFrom(type)) {
            node.setTag(Tag.STR);
         } else {
            node.setTag(Tag.MAP);
         }
      }

   }

   protected Set getProperties(Class type) {
      return this.typeDefinitions.containsKey(type) ? ((TypeDescription)this.typeDefinitions.get(type)).getProperties() : this.getPropertyUtils().getProperties(type);
   }

   protected class RepresentJavaBean implements Represent {
      public Node representData(Object data) {
         return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
      }
   }
}
