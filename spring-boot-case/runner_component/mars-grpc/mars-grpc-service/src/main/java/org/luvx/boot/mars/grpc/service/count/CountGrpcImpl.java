package org.luvx.boot.mars.grpc.service.count;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.luvx.boot.mars.grpc.proto.count.*;

@Slf4j
@GrpcService
public class CountGrpcImpl extends CountRpcGrpc.CountRpcImplBase {
    @Override
    public void getByType(GetByTypeCountRequest request, StreamObserver<GetByTypeCountResponse> responseObserver) {
        super.getByType(request, responseObserver);
    }

    @Override
    public void getByIds(GetByIdsCountRequest request, StreamObserver<GetByIdsCountResponse> responseObserver) {
        super.getByIds(request, responseObserver);
    }

    @Override
    public void operate(CountRequest request, StreamObserver<Empty> responseObserver) {
        super.operate(request, responseObserver);
    }

    @Override
    public void asyncOperate(CountRequest request, StreamObserver<Empty> responseObserver) {
        super.asyncOperate(request, responseObserver);
    }

    @Override
    public void batchSet(BatchSetCountRequest request, StreamObserver<Empty> responseObserver) {
        super.batchSet(request, responseObserver);
    }
}
