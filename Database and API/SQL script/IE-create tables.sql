CREATE TABLE virus
(
    virus_id INTEGER NOT NULL,
    virus_full_name VARCHAR(255) NOT NULL,
    virus_abbreviation VARCHAR(255),

    PRIMARY KEY(virus_id),
    UNIQUE(virus_full_name)
);

CREATE TABLE choicequestion
(
    choice_question_id INTEGER NOT NULL,
    choice_question_content VARCHAR(1500) NOT NULL,
    choice_question_type CHAR(1) NOT NULL,
    answer VARCHAR(10) NOT NULL,
    explanation VARCHAR(1500) NOT NULL,
    choice_question_virus_id INTEGER NOT NULL,
    choice_question_image_binary_code LONGTEXT,

    PRIMARY KEY(choice_question_id),
    FOREIGN KEY(choice_question_virus_id) REFERENCES virus(virus_id),
    CHECK(choice_question_type='s' OR choice_question_type='m')
);

CREATE TABLE choiceoption
(
    choice_option_id INTEGER NOT NULL,
    choice_option_label CHAR(1) NOT NULL,
    choice_option_content VARCHAR(1500) NOT NULL,
    choice_option_question_id INTEGER NOT NULL,
    choice_option_image_binary_code LONGTEXT,
    
    PRIMARY KEY(choice_option_id),
    FOREIGN KEY(choice_option_question_id) REFERENCES choicequestion(choice_question_id),
    CHECK(
          choice_option_label='a' OR 
          choice_option_label='b' OR
          choice_option_label='c' OR
          choice_option_label='d' OR
          choice_option_label='e' OR
          choice_option_label='f'
         )
);

CREATE TABLE virusdescription
(
    des_id INTEGER NOT NULL,
    des_content VARCHAR(1500) NOT NULL,
    des_virus_id INTEGER NOT NULL,

    PRIMARY KEY(des_id),
    FOREIGN KEY(des_virus_id) REFERENCES virus(virus_id)
);

CREATE TABLE virussymptom
(
    sym_id INTEGER NOT NULL,
    sym_content VARCHAR(1500) NOT NULL,
    sym_object_type CHAR(1), -- l(LEAVES), (a)APPEARANCE, (f)FRUIT --
    sym_virus_id INTEGER NOT NULL,

    PRIMARY KEY(sym_id),
    FOREIGN KEY(sym_virus_id) REFERENCES virus(virus_id)
);

CREATE TABLE viruscause
(
    cause_id INTEGER NOT NULL,
    cause_content VARCHAR(1500) NOT NULL,
    cause_type CHAR(1), -- (t)TIMING OF CAUSE, (e)ENVIRONMENTAL CONDITION, (m)MEANS OF INFECTION --
    cause_virus_id INTEGER NOT NULL,

    PRIMARY KEY(cause_id),
    FOREIGN KEY(cause_virus_id) REFERENCES virus(virus_id)
);

CREATE TABLE virusprevention
(
    pre_id INTEGER NOT NULL,
    pre_content VARCHAR(1500) NOT NULL,
    pre_type CHAR(1), -- (c)CHEMICAL CONTROL, (n)NON-CHEMICAL CONTROL --
    pre_virus_id INTEGER NOT NULL,

    PRIMARY KEY(pre_id),
    FOREIGN KEY(pre_virus_id) REFERENCES virus(virus_id)
);

CREATE TABLE nutrient
(
    nutrient_id INTEGER NOT NULL,
    nutrient_name VARCHAR(255) NOT NULL,

    PRIMARY KEY(nutrient_id),
    UNIQUE(nutrient_name)
);

CREATE TABLE nutrientsymptom
(
    sym_id INTEGER NOT NULL,
    sym_content VARCHAR(1500) NOT NULL,
    sym_object_type CHAR(1), -- l(LEAVES), p(PETIOLES), (f)FRUIT, (s)STEMS AND PETIOLES, (t)PLANT, (r)ROOTS --
    sym_nutrient_id INTEGER NOT NULL,

    PRIMARY KEY(sym_id),
    FOREIGN KEY(sym_nutrient_id) REFERENCES nutrient(nutrient_id)
);

CREATE TABLE nutrientreason
(
    reason_id INTEGER NOT NULL,
    reason_content VARCHAR(1500) NOT NULL,
    reason_nutrient_id INTEGER NOT NULL,

    PRIMARY KEY(reason_id),
    FOREIGN KEY(reason_nutrient_id) REFERENCES nutrient(nutrient_id)
);

CREATE TABLE nutrientfactor
(
    factor_id INTEGER NOT NULL,
    factor_content VARCHAR(1500) NOT NULL,
    factor_nutrient_id INTEGER NOT NULL,

    PRIMARY KEY(factor_id),
    FOREIGN KEY(factor_nutrient_id) REFERENCES nutrient(nutrient_id)
);

CREATE TABLE nutrientcorrectionmethod
(
    method_id INTEGER NOT NULL,
    method_content VARCHAR(1500) NOT NULL,
    method_nutrient_id INTEGER NOT NULL,

    PRIMARY KEY(method_id),
    FOREIGN KEY(method_nutrient_id) REFERENCES nutrient(nutrient_id)
);