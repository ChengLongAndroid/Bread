// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/MapServiceRespCmdProto.proto

package com.funcy.g01.base.proto.bo.service;

public final class MapServiceRespProtoBuffer {
  private MapServiceRespProtoBuffer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface GetRoleMapInfoListOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // repeated .proto.ServerMapInfoProto fightMapInfoList = 1;
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> 
        getFightMapInfoListList();
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto getFightMapInfoList(int index);
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    int getFightMapInfoListCount();
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    java.util.List<? extends com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder> 
        getFightMapInfoListOrBuilderList();
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder getFightMapInfoListOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code proto.GetRoleMapInfoList}
   */
  public static final class GetRoleMapInfoList extends
      com.google.protobuf.GeneratedMessage
      implements GetRoleMapInfoListOrBuilder {
    // Use GetRoleMapInfoList.newBuilder() to construct.
    private GetRoleMapInfoList(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private GetRoleMapInfoList(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final GetRoleMapInfoList defaultInstance;
    public static GetRoleMapInfoList getDefaultInstance() {
      return defaultInstance;
    }

    public GetRoleMapInfoList getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private GetRoleMapInfoList(
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
                fightMapInfoList_ = new java.util.ArrayList<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto>();
                mutable_bitField0_ |= 0x00000001;
              }
              fightMapInfoList_.add(input.readMessage(com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.PARSER, extensionRegistry));
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
          fightMapInfoList_ = java.util.Collections.unmodifiableList(fightMapInfoList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.internal_static_proto_GetRoleMapInfoList_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.internal_static_proto_GetRoleMapInfoList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.class, com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.Builder.class);
    }

    public static com.google.protobuf.Parser<GetRoleMapInfoList> PARSER =
        new com.google.protobuf.AbstractParser<GetRoleMapInfoList>() {
      public GetRoleMapInfoList parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GetRoleMapInfoList(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<GetRoleMapInfoList> getParserForType() {
      return PARSER;
    }

    // repeated .proto.ServerMapInfoProto fightMapInfoList = 1;
    public static final int FIGHTMAPINFOLIST_FIELD_NUMBER = 1;
    private java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> fightMapInfoList_;
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    public java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> getFightMapInfoListList() {
      return fightMapInfoList_;
    }
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    public java.util.List<? extends com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder> 
        getFightMapInfoListOrBuilderList() {
      return fightMapInfoList_;
    }
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    public int getFightMapInfoListCount() {
      return fightMapInfoList_.size();
    }
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto getFightMapInfoList(int index) {
      return fightMapInfoList_.get(index);
    }
    /**
     * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
     */
    public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder getFightMapInfoListOrBuilder(
        int index) {
      return fightMapInfoList_.get(index);
    }

    private void initFields() {
      fightMapInfoList_ = java.util.Collections.emptyList();
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
      for (int i = 0; i < fightMapInfoList_.size(); i++) {
        output.writeMessage(1, fightMapInfoList_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < fightMapInfoList_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, fightMapInfoList_.get(i));
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

    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList prototype) {
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
     * Protobuf type {@code proto.GetRoleMapInfoList}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoListOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.internal_static_proto_GetRoleMapInfoList_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.internal_static_proto_GetRoleMapInfoList_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.class, com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.Builder.class);
      }

      // Construct using com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.newBuilder()
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
          getFightMapInfoListFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (fightMapInfoListBuilder_ == null) {
          fightMapInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          fightMapInfoListBuilder_.clear();
        }
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.internal_static_proto_GetRoleMapInfoList_descriptor;
      }

      public com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList getDefaultInstanceForType() {
        return com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.getDefaultInstance();
      }

      public com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList build() {
        com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList buildPartial() {
        com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList result = new com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList(this);
        int from_bitField0_ = bitField0_;
        if (fightMapInfoListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            fightMapInfoList_ = java.util.Collections.unmodifiableList(fightMapInfoList_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.fightMapInfoList_ = fightMapInfoList_;
        } else {
          result.fightMapInfoList_ = fightMapInfoListBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList) {
          return mergeFrom((com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList other) {
        if (other == com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList.getDefaultInstance()) return this;
        if (fightMapInfoListBuilder_ == null) {
          if (!other.fightMapInfoList_.isEmpty()) {
            if (fightMapInfoList_.isEmpty()) {
              fightMapInfoList_ = other.fightMapInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureFightMapInfoListIsMutable();
              fightMapInfoList_.addAll(other.fightMapInfoList_);
            }
            onChanged();
          }
        } else {
          if (!other.fightMapInfoList_.isEmpty()) {
            if (fightMapInfoListBuilder_.isEmpty()) {
              fightMapInfoListBuilder_.dispose();
              fightMapInfoListBuilder_ = null;
              fightMapInfoList_ = other.fightMapInfoList_;
              bitField0_ = (bitField0_ & ~0x00000001);
              fightMapInfoListBuilder_ = 
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                   getFightMapInfoListFieldBuilder() : null;
            } else {
              fightMapInfoListBuilder_.addAllMessages(other.fightMapInfoList_);
            }
          }
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
        com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // repeated .proto.ServerMapInfoProto fightMapInfoList = 1;
      private java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> fightMapInfoList_ =
        java.util.Collections.emptyList();
      private void ensureFightMapInfoListIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          fightMapInfoList_ = new java.util.ArrayList<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto>(fightMapInfoList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilder<
          com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder> fightMapInfoListBuilder_;

      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> getFightMapInfoListList() {
        if (fightMapInfoListBuilder_ == null) {
          return java.util.Collections.unmodifiableList(fightMapInfoList_);
        } else {
          return fightMapInfoListBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public int getFightMapInfoListCount() {
        if (fightMapInfoListBuilder_ == null) {
          return fightMapInfoList_.size();
        } else {
          return fightMapInfoListBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto getFightMapInfoList(int index) {
        if (fightMapInfoListBuilder_ == null) {
          return fightMapInfoList_.get(index);
        } else {
          return fightMapInfoListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder setFightMapInfoList(
          int index, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto value) {
        if (fightMapInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.set(index, value);
          onChanged();
        } else {
          fightMapInfoListBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder setFightMapInfoList(
          int index, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder builderForValue) {
        if (fightMapInfoListBuilder_ == null) {
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.set(index, builderForValue.build());
          onChanged();
        } else {
          fightMapInfoListBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder addFightMapInfoList(com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto value) {
        if (fightMapInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.add(value);
          onChanged();
        } else {
          fightMapInfoListBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder addFightMapInfoList(
          int index, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto value) {
        if (fightMapInfoListBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.add(index, value);
          onChanged();
        } else {
          fightMapInfoListBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder addFightMapInfoList(
          com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder builderForValue) {
        if (fightMapInfoListBuilder_ == null) {
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.add(builderForValue.build());
          onChanged();
        } else {
          fightMapInfoListBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder addFightMapInfoList(
          int index, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder builderForValue) {
        if (fightMapInfoListBuilder_ == null) {
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.add(index, builderForValue.build());
          onChanged();
        } else {
          fightMapInfoListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder addAllFightMapInfoList(
          java.lang.Iterable<? extends com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto> values) {
        if (fightMapInfoListBuilder_ == null) {
          ensureFightMapInfoListIsMutable();
          super.addAll(values, fightMapInfoList_);
          onChanged();
        } else {
          fightMapInfoListBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder clearFightMapInfoList() {
        if (fightMapInfoListBuilder_ == null) {
          fightMapInfoList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          fightMapInfoListBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public Builder removeFightMapInfoList(int index) {
        if (fightMapInfoListBuilder_ == null) {
          ensureFightMapInfoListIsMutable();
          fightMapInfoList_.remove(index);
          onChanged();
        } else {
          fightMapInfoListBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder getFightMapInfoListBuilder(
          int index) {
        return getFightMapInfoListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder getFightMapInfoListOrBuilder(
          int index) {
        if (fightMapInfoListBuilder_ == null) {
          return fightMapInfoList_.get(index);  } else {
          return fightMapInfoListBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public java.util.List<? extends com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder> 
           getFightMapInfoListOrBuilderList() {
        if (fightMapInfoListBuilder_ != null) {
          return fightMapInfoListBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(fightMapInfoList_);
        }
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder addFightMapInfoListBuilder() {
        return getFightMapInfoListFieldBuilder().addBuilder(
            com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.getDefaultInstance());
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder addFightMapInfoListBuilder(
          int index) {
        return getFightMapInfoListFieldBuilder().addBuilder(
            index, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.getDefaultInstance());
      }
      /**
       * <code>repeated .proto.ServerMapInfoProto fightMapInfoList = 1;</code>
       */
      public java.util.List<com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder> 
           getFightMapInfoListBuilderList() {
        return getFightMapInfoListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilder<
          com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder> 
          getFightMapInfoListFieldBuilder() {
        if (fightMapInfoListBuilder_ == null) {
          fightMapInfoListBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
              com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto.Builder, com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProtoOrBuilder>(
                  fightMapInfoList_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          fightMapInfoList_ = null;
        }
        return fightMapInfoListBuilder_;
      }

      // @@protoc_insertion_point(builder_scope:proto.GetRoleMapInfoList)
    }

    static {
      defaultInstance = new GetRoleMapInfoList(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:proto.GetRoleMapInfoList)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_GetRoleMapInfoList_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_GetRoleMapInfoList_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\"proto/MapServiceRespCmdProto.proto\022\005pr" +
      "oto\032\033proto/SynFightBoProto.proto\"I\n\022GetR" +
      "oleMapInfoList\0223\n\020fightMapInfoList\030\001 \003(\013" +
      "2\031.proto.ServerMapInfoProtoB@\n#com.funcy" +
      ".g01.base.proto.bo.serviceB\031MapServiceRe" +
      "spProtoBuffer"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_proto_GetRoleMapInfoList_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_proto_GetRoleMapInfoList_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_proto_GetRoleMapInfoList_descriptor,
              new java.lang.String[] { "FightMapInfoList", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
