package com;
import java.lang.*;

public class RecordShelf
{
    public String record_id, test_type, prescribing_date, test_date, diagnosis, description, name, agg_score;

    /* Data structure class for records. */
    public RecordShelf(String[] params)
    {
	record_id = params[0];
	test_type = params[1];
	prescribing_date = params[2];
	test_date = params[3];
	diagnosis = params[4];
	description = params[5];
	name = params[6];
	agg_score = params[7];
    }
}
