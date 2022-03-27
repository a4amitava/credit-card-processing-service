package com.sapient.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sapient.test.validation.CardNumberValidationConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

import static com.sapient.test.validation.ValidationMessages.CARD_NUMBER_MUST_BE_OF_2_DIGITS_OR_MORE;
import static com.sapient.test.validation.ValidationMessages.INVALID_CARD_NUMBER;
import static com.sapient.test.validation.ValidationMessages.PRINTABLE_CARD_NAME_IS_MANDATORY;


@Entity
@Builder
@AllArgsConstructor
@Getter
@Jacksonized
@Data
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long cardSequenceId;

    @CardNumberValidationConstraint(message = INVALID_CARD_NUMBER)
    @Min(value = 10, message = CARD_NUMBER_MUST_BE_OF_2_DIGITS_OR_MORE)
    private Long cardNumber;

    @NotBlank(message = PRINTABLE_CARD_NAME_IS_MANDATORY)
    private String printableName;

    private BigDecimal cardLimit;

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    protected CardDetails() {
    }

}
