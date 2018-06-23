// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/ActiveServiceRespCmdProto.proto

package com.funcy.g01.base.proto.service;

public final class ActiveServiceRespProtoBuffer {
  private ActiveServiceRespProtoBuffer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface OpenChestOnSlotProtoOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // repeated .proto.ItemVoProto items = 1;
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> 
        getItemsList();
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto getItems(int index);
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    int getItemsCount();
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    java.util.List<? extends com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder> 
        getItemsOrBuilderList();
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder getItemsOrBuilder(
        int index);

    // optional .proto.ChestSlotProto chestSlot = 2;
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    boolean hasChestSlot();
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto getChestSlot();
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder getChestSlotOrBuilder();

    // optional bool isOpen = 3;
    /**
     * <code>optional bool isOpen = 3;</code>
     */
    boolean hasIsOpen();
    /**
     * <code>optional bool isOpen = 3;</code>
     */
    boolean getIsOpen();
  }
  /**
   * Protobuf type {@code proto.OpenChestOnSlotProto}
   */
  public static final class OpenChestOnSlotProto extends
      com.google.protobuf.GeneratedMessage
      implements OpenChestOnSlotProtoOrBuilder {
    // Use OpenChestOnSlotProto.newBuilder() to construct.
    private OpenChestOnSlotProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private OpenChestOnSlotProto(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final OpenChestOnSlotProto defaultInstance;
    public static OpenChestOnSlotProto getDefaultInstance() {
      return defaultInstance;
    }

    public OpenChestOnSlotProto getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private OpenChestOnSlotProto(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                items_ = new java.util.ArrayList<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto>();
                mutable_bitField0_ |= 0x00000001;
              }
              items_.add(input.readMessage(com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.PARSER, extensionRegistry));
              break;
            }
            case 18: {
              com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = chestSlot_.toBuilder();
              }
              chestSlot_ = input.readMessage(com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(chestSlot_);
                chestSlot_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000002;
              isOpen_ = input.readBool();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          items_ = java.util.Collections.unmodifiableList(items_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.internal_static_proto_OpenChestOnSlotProto_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.internal_static_proto_OpenChestOnSlotProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.class, com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.Builder.class);
    }

    public static com.google.protobuf.Parser<OpenChestOnSlotProto> PARSER =
        new com.google.protobuf.AbstractParser<OpenChestOnSlotProto>() {
      public OpenChestOnSlotProto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new OpenChestOnSlotProto(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<OpenChestOnSlotProto> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // repeated .proto.ItemVoProto items = 1;
    public static final int ITEMS_FIELD_NUMBER = 1;
    private java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> items_;
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    public java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> getItemsList() {
      return items_;
    }
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    public java.util.List<? extends com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder> 
        getItemsOrBuilderList() {
      return items_;
    }
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    public int getItemsCount() {
      return items_.size();
    }
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto getItems(int index) {
      return items_.get(index);
    }
    /**
     * <code>repeated .proto.ItemVoProto items = 1;</code>
     */
    public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder getItemsOrBuilder(
        int index) {
      return items_.get(index);
    }

    // optional .proto.ChestSlotProto chestSlot = 2;
    public static final int CHESTSLOT_FIELD_NUMBER = 2;
    private com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto chestSlot_;
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    public boolean hasChestSlot() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto getChestSlot() {
      return chestSlot_;
    }
    /**
     * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
     */
    public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder getChestSlotOrBuilder() {
      return chestSlot_;
    }

    // optional bool isOpen = 3;
    public static final int ISOPEN_FIELD_NUMBER = 3;
    private boolean isOpen_;
    /**
     * <code>optional bool isOpen = 3;</code>
     */
    public boolean hasIsOpen() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional bool isOpen = 3;</code>
     */
    public boolean getIsOpen() {
      return isOpen_;
    }

    private void initFields() {
      items_ = java.util.Collections.emptyList();
      chestSlot_ = com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.getDefaultInstance();
      isOpen_ = false;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < items_.size(); i++) {
        output.writeMessage(1, items_.get(i));
      }
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(2, chestSlot_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBool(3, isOpen_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < items_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, items_.get(i));
      }
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, chestSlot_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, isOpen_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code proto.OpenChestOnSlotProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.internal_static_proto_OpenChestOnSlotProto_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.internal_static_proto_OpenChestOnSlotProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.class, com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.Builder.class);
      }

      // Construct using com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getItemsFieldBuilder();
          getChestSlotFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (itemsBuilder_ == null) {
          items_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          itemsBuilder_.clear();
        }
        if (chestSlotBuilder_ == null) {
          chestSlot_ = com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.getDefaultInstance();
        } else {
          chestSlotBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        isOpen_ = false;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.internal_static_proto_OpenChestOnSlotProto_descriptor;
      }

      public com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto getDefaultInstanceForType() {
        return com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.getDefaultInstance();
      }

      public com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto build() {
        com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto buildPartial() {
        com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto result = new com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (itemsBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            items_ = java.util.Collections.unmodifiableList(items_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.items_ = items_;
        } else {
          result.items_ = itemsBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000001;
        }
        if (chestSlotBuilder_ == null) {
          result.chestSlot_ = chestSlot_;
        } else {
          result.chestSlot_ = chestSlotBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000002;
        }
        result.isOpen_ = isOpen_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto) {
          return mergeFrom((com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto other) {
        if (other == com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto.getDefaultInstance()) return this;
        if (itemsBuilder_ == null) {
          if (!other.items_.isEmpty()) {
            if (items_.isEmpty()) {
              items_ = other.items_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureItemsIsMutable();
              items_.addAll(other.items_);
            }
            onChanged();
          }
        } else {
          if (!other.items_.isEmpty()) {
            if (itemsBuilder_.isEmpty()) {
              itemsBuilder_.dispose();
              itemsBuilder_ = null;
              items_ = other.items_;
              bitField0_ = (bitField0_ & ~0x00000001);
              itemsBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getItemsFieldBuilder() : null;
            } else {
              itemsBuilder_.addAllMessages(other.items_);
            }
          }
        }
        if (other.hasChestSlot()) {
          mergeChestSlot(other.getChestSlot());
        }
        if (other.hasIsOpen()) {
          setIsOpen(other.getIsOpen());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // repeated .proto.ItemVoProto items = 1;
      private java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> items_ =
        java.util.Collections.emptyList();
      private void ensureItemsIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          items_ = new java.util.ArrayList<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto>(items_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilder<
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder> itemsBuilder_;

      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> getItemsList() {
        if (itemsBuilder_ == null) {
          return java.util.Collections.unmodifiableList(items_);
        } else {
          return itemsBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public int getItemsCount() {
        if (itemsBuilder_ == null) {
          return items_.size();
        } else {
          return itemsBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto getItems(int index) {
        if (itemsBuilder_ == null) {
          return items_.get(index);
        } else {
          return itemsBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder setItems(
          int index, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto value) {
        if (itemsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemsIsMutable();
          items_.set(index, value);
          onChanged();
        } else {
          itemsBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder setItems(
          int index, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder builderForValue) {
        if (itemsBuilder_ == null) {
          ensureItemsIsMutable();
          items_.set(index, builderForValue.build());
          onChanged();
        } else {
          itemsBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder addItems(com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto value) {
        if (itemsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemsIsMutable();
          items_.add(value);
          onChanged();
        } else {
          itemsBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder addItems(
          int index, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto value) {
        if (itemsBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureItemsIsMutable();
          items_.add(index, value);
          onChanged();
        } else {
          itemsBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder addItems(
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder builderForValue) {
        if (itemsBuilder_ == null) {
          ensureItemsIsMutable();
          items_.add(builderForValue.build());
          onChanged();
        } else {
          itemsBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder addItems(
          int index, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder builderForValue) {
        if (itemsBuilder_ == null) {
          ensureItemsIsMutable();
          items_.add(index, builderForValue.build());
          onChanged();
        } else {
          itemsBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder addAllItems(
          java.lang.Iterable<? extends com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto> values) {
        if (itemsBuilder_ == null) {
          ensureItemsIsMutable();
          super.addAll(values, items_);
          onChanged();
        } else {
          itemsBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder clearItems() {
        if (itemsBuilder_ == null) {
          items_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          itemsBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public Builder removeItems(int index) {
        if (itemsBuilder_ == null) {
          ensureItemsIsMutable();
          items_.remove(index);
          onChanged();
        } else {
          itemsBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder getItemsBuilder(
          int index) {
        return getItemsFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder getItemsOrBuilder(
          int index) {
        if (itemsBuilder_ == null) {
          return items_.get(index);  } else {
          return itemsBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public java.util.List<? extends com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder> 
           getItemsOrBuilderList() {
        if (itemsBuilder_ != null) {
          return itemsBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(items_);
        }
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder addItemsBuilder() {
        return getItemsFieldBuilder().addBuilder(
            com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.getDefaultInstance());
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder addItemsBuilder(
          int index) {
        return getItemsFieldBuilder().addBuilder(
            index, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.getDefaultInstance());
      }
      /**
       * <code>repeated .proto.ItemVoProto items = 1;</code>
       */
      public java.util.List<com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder> 
           getItemsBuilderList() {
        return getItemsFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder> 
          getItemsFieldBuilder() {
        if (itemsBuilder_ == null) {
          itemsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProtoOrBuilder>(
                  items_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          items_ = null;
        }
        return itemsBuilder_;
      }

      // optional .proto.ChestSlotProto chestSlot = 2;
      private com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto chestSlot_ = com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder> chestSlotBuilder_;
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public boolean hasChestSlot() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto getChestSlot() {
        if (chestSlotBuilder_ == null) {
          return chestSlot_;
        } else {
          return chestSlotBuilder_.getMessage();
        }
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public Builder setChestSlot(com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto value) {
        if (chestSlotBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          chestSlot_ = value;
          onChanged();
        } else {
          chestSlotBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public Builder setChestSlot(
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder builderForValue) {
        if (chestSlotBuilder_ == null) {
          chestSlot_ = builderForValue.build();
          onChanged();
        } else {
          chestSlotBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public Builder mergeChestSlot(com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto value) {
        if (chestSlotBuilder_ == null) {
          if (((bitField0_ & 0x00000002) == 0x00000002) &&
              chestSlot_ != com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.getDefaultInstance()) {
            chestSlot_ =
              com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.newBuilder(chestSlot_).mergeFrom(value).buildPartial();
          } else {
            chestSlot_ = value;
          }
          onChanged();
        } else {
          chestSlotBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        return this;
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public Builder clearChestSlot() {
        if (chestSlotBuilder_ == null) {
          chestSlot_ = com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.getDefaultInstance();
          onChanged();
        } else {
          chestSlotBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder getChestSlotBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getChestSlotFieldBuilder().getBuilder();
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      public com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder getChestSlotOrBuilder() {
        if (chestSlotBuilder_ != null) {
          return chestSlotBuilder_.getMessageOrBuilder();
        } else {
          return chestSlot_;
        }
      }
      /**
       * <code>optional .proto.ChestSlotProto chestSlot = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilder<
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder> 
          getChestSlotFieldBuilder() {
        if (chestSlotBuilder_ == null) {
          chestSlotBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto.Builder, com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProtoOrBuilder>(
                  chestSlot_,
                  getParentForChildren(),
                  isClean());
          chestSlot_ = null;
        }
        return chestSlotBuilder_;
      }

      // optional bool isOpen = 3;
      private boolean isOpen_ ;
      /**
       * <code>optional bool isOpen = 3;</code>
       */
      public boolean hasIsOpen() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional bool isOpen = 3;</code>
       */
      public boolean getIsOpen() {
        return isOpen_;
      }
      /**
       * <code>optional bool isOpen = 3;</code>
       */
      public Builder setIsOpen(boolean value) {
        bitField0_ |= 0x00000004;
        isOpen_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool isOpen = 3;</code>
       */
      public Builder clearIsOpen() {
        bitField0_ = (bitField0_ & ~0x00000004);
        isOpen_ = false;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:proto.OpenChestOnSlotProto)
    }

    static {
      defaultInstance = new OpenChestOnSlotProto(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:proto.OpenChestOnSlotProto)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_OpenChestOnSlotProto_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_OpenChestOnSlotProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n%proto/ActiveServiceRespCmdProto.proto\022" +
      "\005proto\032\031proto/GlobalBoProto.proto\"s\n\024Ope" +
      "nChestOnSlotProto\022!\n\005items\030\001 \003(\0132\022.proto" +
      ".ItemVoProto\022(\n\tchestSlot\030\002 \001(\0132\025.proto." +
      "ChestSlotProto\022\016\n\006isOpen\030\003 \001(\010B@\n com.fu" +
      "ncy.g01.base.proto.serviceB\034ActiveServic" +
      "eRespProtoBuffer"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_proto_OpenChestOnSlotProto_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_proto_OpenChestOnSlotProto_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_proto_OpenChestOnSlotProto_descriptor,
              new java.lang.String[] { "Items", "ChestSlot", "IsOpen", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
