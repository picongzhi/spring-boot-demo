package com.pcz.mq.rabbitmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author picongzhi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStruct implements Serializable {
    private static final long serialVersionUID = 3836194313484883352L;

    private String message;
}
