// package org.luvx.boot.mars.grpc.sdk.user;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import java.util.Collection;
// import java.util.Map;
//
// import static java.util.Collections.emptyMap;
// import static java.util.stream.Collectors.toList;
// import static java.util.stream.Collectors.toMap;
// import static org.apache.commons.collections4.CollectionUtils.isEmpty;
//
// import java.util.Collection;
// import java.util.Map;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.stereotype.Service;
//
// import com.acfun.common.model.ComboCountId;
// import com.acfun.common.model.ComboCountType;
// import com.acfun.common.utils.CountUtils;
// import com.acfun.count.constant.CountEvent;
// import com.acfun.count.service.CountService;
// import com.acfun.meow.constants.MeowCountType;
// import com.acfun.meow.model.MeowCounts;
// import com.acfun.meow.service.MeowCountService;
//
//
// @Slf4j
// public class UserCountService {
//     @Autowired
//     private CountService countService;
//
//     public Map<Long, Integer> getByType(Collection<Long> meowIds, MeowCountType type) {
//         if (isEmpty(meowIds)) {
//             return emptyMap();
//         }
//         Collection<Long> countIds = meowIds.stream().map(this::genCountId).collect(toList());
//         Map<Long, Integer> counts = countService.getByType(countIds, type.getValue());
//
//         return counts.entrySet().stream()
//                 .collect(toMap(entry -> genMeowId(entry.getKey()), Map.Entry::getValue));
//     }
//
//     public void operate(CountEvent event, long meowId, Collection<MeowCountType> types, int value) {
//         countService.operate(event, genCountId(meowId),
//                 types.stream().map(MeowCountType::getValue).collect(toList()), value);
//     }
//
//     public void asyncOperate(CountEvent event, long meowId, Collection<MeowCountType> types,
//                              int value) {
//         countService.asyncOperate(event, genCountId(meowId),
//                 types.stream().map(MeowCountType::getValue).collect(toList()), value);
//
//     }
//
//     public Map<Long, MeowCounts> getByIds(Collection<Long> ids) {
//         if (isEmpty(ids)) {
//             return emptyMap();
//         }
//
//         Collection<Long> countIds = ids.stream().map(this::genCountId).collect(toList());
//         Map<Long, Map<Integer, Integer>> result = countService.getByIds(countIds);
//
//         return result.entrySet().stream()
//                 .collect(toMap(e -> genMeowId(e.getKey()), e -> new MeowCounts(e.getValue())));
//     }
//
//     private long genCountId(long id) {
//         return CountUtils.genCountId(new ComboCountId(id, ComboCountType.MEOW));
//     }
//
//     private long genMeowId(long countId) {
//         return CountUtils.parseComboId(countId).getSourceId();
//     }
// }
