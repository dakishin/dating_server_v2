-- Function: calc_distance(double precision, double precision, double precision, double precision)

-- DROP FUNCTION calc_distance(double precision, double precision, double precision, double precision);

CREATE OR REPLACE FUNCTION calc_distance(
    latitude1 double precision,
    longitude1 double precision,
    latitude2 double precision,
    longitude2 double precision)
  RETURNS integer AS
$BODY$
BEGIN



  RETURN
  (round (6367 * 2 * atan2(sqrt(

                        pow(sin(((latitude1 - latitude2) * 0.0174532925199433) / 2.0), 2)
                        + cos(latitude2 * 0.0174532925199433) * cos(latitude1 * 0.0174532925199433) * pow(sin(((longitude1 - longitude2) * 0.0174532925199433) / 2.0), 2)

                    ), sqrt(1 -

                            pow(sin(((latitude1 - latitude2) * 0.0174532925199433) / 2.0), 2)
                            + cos(latitude2 * 0.0174532925199433) * cos(latitude1 * 0.0174532925199433) * pow(sin(((longitude1 - longitude2) * 0.0174532925199433) / 2.0), 2)
                    )))::integer);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION calc_distance(double precision, double precision, double precision, double precision)
  OWNER TO postgres;
