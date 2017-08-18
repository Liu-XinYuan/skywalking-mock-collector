package org.skywalking.apm.mock.collector.service;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.mock.collector.entity.Segment;
import org.skywalking.apm.mock.collector.entity.ValidateData;
import org.skywalking.apm.network.proto.Downstream;
import org.skywalking.apm.network.proto.KeyWithStringValue;
import org.skywalking.apm.network.proto.LogMessage;
import org.skywalking.apm.network.proto.SpanObject;
import org.skywalking.apm.network.proto.TraceSegmentObject;
import org.skywalking.apm.network.proto.TraceSegmentReference;
import org.skywalking.apm.network.proto.TraceSegmentServiceGrpc;
import org.skywalking.apm.network.proto.UpstreamSegment;

public class MockTraceSegmentService extends TraceSegmentServiceGrpc.TraceSegmentServiceImplBase {

    private Logger logger = LogManager.getLogger(MockTraceSegmentService.class);

    @Override
    public StreamObserver<UpstreamSegment> collect(final StreamObserver<Downstream> responseObserver) {
        return new StreamObserver<UpstreamSegment>() {
            public void onNext(UpstreamSegment value) {
                ValidateData.INSTANCE.getSegmentItem().addSegmentItem(value.getSegment());
            }

            public void onError(Throwable t) {

            }

            public void onCompleted() {
                responseObserver.onNext(Downstream.getDefaultInstance());
                responseObserver.onCompleted();
            }
        };
    }
}
