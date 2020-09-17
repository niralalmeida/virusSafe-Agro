CREATE TABLE virus
(
    virus_id INTEGER NOT NULL,
    virus_full_name VARCHAR(255) NOT NULL,
    virus_abbreviation VARCHAR(255),
    virus_description VARCHAR(1500),
    symptoms VARCHAR(1500),
    causes VARCHAR(1500),
    spread VARCHAR(1500),
    prevention VARCHAR(1500),
    virus_distribution VARCHAR(1500),

    PRIMARY KEY(virus_id),
    UNIQUE(virus_full_name)
);

CREATE TABLE choicequestion
(
    choice_question_id INTEGER NOT NULL,
    choice_question_content VARCHAR(1500) NOT NULL,
    choice_question_type CHAR(1) NOT NULL,
    answer VARCHAR(10) NOT NULL,
    choice_question_virus_id INTEGER NOT NULL,

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

CREATE TABLE nutrient
(
    nutrient_id INTEGER NOT NULL,
    nutrient_name VARCHAR(255) NOT NULL,
    nutrient_symptoms VARCHAR(1500),
    nutrient_reasons VARCHAR(1500),
    nutrient_factory VARCHAR(1500),
    nutrient_correction_method VARCHAR(1500),

    PRIMARY KEY(nutrient_id),
    UNIQUE(nutrient_name)
);