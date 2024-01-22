package org.luvx.boot.tools.service.shorturl.converter;

import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.luvx.coding.common.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@Service
public class TrieDomainProvider implements BaseDomainProvider {
    private final char[]                  defaultShortUrl = new char[0];
    /**
     * root节点
     */
    private final TrieNode                root            = new TrieNode('0', null, defaultShortUrl, null);
    /**
     * 节点信息维护的锁
     */
    private final ReentrantLock           lock            = new ReentrantLock();
    /**
     * 叶子节点的map，方便通过叶子节点中的短链接信息，遍历到长链接信息
     */
    private final Map<TrieNode, TrieNode> short2LongMap   = Maps.newConcurrentMap();

    @Resource
    private BaseDomainConverter baseDomainConverter;

    @Override
    public String getLongUrl(String shortUrl) {
        TrieNode leafNode = short2LongMap.get(new TrieNode('0', null, shortUrl.toCharArray(), null));
        if (leafNode == null) {
            log.error("无效短链接:{}", shortUrl);
            throw new BizException("无效短链接");
        }
        StringBuilder sb = new StringBuilder();
        while (leafNode.parent != null && leafNode.value != '0') {
            sb.append(leafNode.value);
            leafNode = leafNode.parent;
        }
        return sb.reverse().toString();
    }

    @Override
    public String getShortUrl(String longUrl) {
        if (StringUtils.isEmpty(longUrl)) {
            return StringUtils.EMPTY;
        }
        char[] keyChars = longUrl.toCharArray();
        TrieNode parent = root, keyLastNode = parent;
        for (int i = 0; i < keyChars.length; i++) {
            TrieNode node;
            char c = keyChars[i];
            if (i < keyChars.length - 1) {
                node = buildNode(parent, c, false, longUrl);
            } else {
                node = buildNode(parent, c, true, longUrl);
                keyLastNode = node;
            }
            parent = node;
        }
        return new String(keyLastNode.shortUrl);
    }

    private TrieNode buildNode(TrieNode parent, char nodeVal, boolean isLast, String longUrl) {
        TrieNode cur;
        lock.lock();
        try {
            Map<Character, TrieNode> children = parent.getChildren();
            TrieNode toAdd = children.get(nodeVal);
            if (toAdd != null) {
                cur = toAdd;
            } else {
                cur = new TrieNode(nodeVal, null, null, parent);
                children.put(nodeVal, cur);
            }
        } finally {
            lock.unlock();
        }
        if (isLast) {
            if (isEmpty(cur.shortUrl)) {
                cur.shortUrl = baseDomainConverter.shorten(longUrl).toCharArray();
                short2LongMap.put(cur, cur);
            }
        }
        return cur;
    }

    /**
     * 会被用于 map 的key, 对象创建后避免属性二次修改
     */
    public static class TrieNode {
        private final char                     value;
        /**
         * 通常情况下只有 62 中字符, 使用长度 62 的数组也行
         */
        private       Map<Character, TrieNode> children;
        /**
         * 不使用 String,
         * 预计最大只有 8 位, 计算 hashcode 不会过于耗时
         * 不可二次写入
         */
        private       char[]                   shortUrl;
        private final TrieNode                 parent;

        public TrieNode(char value,
                        Map<Character, TrieNode> children, char[] shortUrl,
                        TrieNode parent) {
            this.value = value;
            this.children = children;
            this.shortUrl = shortUrl;
            this.parent = parent;
        }

        public Map<Character, TrieNode> getChildren() {
            if (children == null) {
                children = Maps.newHashMap();
            }
            return children;
        }

        // public boolean isLeaf() {
        //     return isNotEmpty(children);
        // }

        // public boolean isValid() {
        //     return isLeaf() && isNotEmpty(shortUrl);
        // }

        @Override
        public int hashCode() {
            return Arrays.hashCode(shortUrl);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TrieNode that = (TrieNode) obj;
            return Arrays.equals(this.shortUrl, that.shortUrl);
        }
    }
}
