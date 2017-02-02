package be.labo.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NoZero {
	final static int maxNoZero = 127; // Max record length

/*
  zero_encode: Returns a data record simply prefixed by its length
    If data contains more than 1/8 of zero bytes, a bit array prefixes the data to indicate where zero bytes are removed.
    The result of the function is the size of the generated output and 255 in case of error.
    A kind of "sparse byte string compression"...
    CAUTION: This algorithm is limited to records with a max length of 127
    Parameters:
    bytes_to_encode: input array of bytes
    bytes_to_decode.limit(): length of this array
    result: output array of bytes (must be at least one byte bigger than input array)
    result_max_len: protection against overrun!
  */
static public ByteBuffer encode(ByteBuffer bytes_to_encode) {
  if (bytes_to_encode.limit() > maxNoZero) return null; // No more than 127 bytes of data can be processed
  ByteBuffer result = ByteBuffer.allocate(maxNoZero+1);
  result.order(bytes_to_encode.order()); //CALLER MUST TAKE GREAT CARE OF THIS IN MOST CASE!!!

  int nbZ =0;
  for (int i=0; i < bytes_to_encode.limit(); i++){ // How many zero bytes?
    if (bytes_to_encode.get(i) == 0) nbZ++;
  }
  int lgPref = (bytes_to_encode.limit()+7) >> 3; // bit array size will be 1/8th of input bytes number

  if (nbZ > lgPref) { // More than 1/8 of zero bytes?
    result.put(0, (byte) (0xFF & (128+bytes_to_encode.limit()))); // length+128
    int pr = lgPref;
    int bitp = 0;
    int bitbyte = 0;
    for (int i=0; i < bytes_to_encode.limit(); i++) {
      if (bitp == 0) {
        result.put(1+bitbyte, (byte)0);
      }
      if(bytes_to_encode.get(i) != 0) {
        pr++;
        result.put(pr, bytes_to_encode.get(i));
      }
      else {
        result.put(1+bitbyte, (byte) (result.get(1+bitbyte) | (1 << bitp)));
      }
      bitp++;
      if (bitp > 7) {
        bitp=0;
        bitbyte++;
      }
    }
    result.limit(1+pr);
    return result;
  }
  else {  // Not a lot of zeroes: keep data as is...
    result.put(0, (byte) (0xFF & bytes_to_encode.limit())); // one byte prefix containing length
    for (int i=0; i < bytes_to_encode.limit(); i++) {
      result.put(i+1, bytes_to_encode.get(i));
    }
    result.limit(1+bytes_to_encode.limit());
    return result;
  }
}

/*
  zero_decode: Returns a data record from a "compressed" form
    The result of the function is the size of the input which was interpreted to generate the output and 255 in case of errror.
    A kind of "sparse byte string decompression"...
    CAUTION: This algorithm is limited to input records with a max length of 128 (output max length is 127)
    Returns: number of input bytes used to generate the output (beware of output truncation is results_max_len is too short)
    Parameters:
    bytes_to_dencode: input array of bytes
    bytes_to_decode.limit(): length of this array
    result: output array of bytes (may be 7,5 times bigger than compressed input!)
    result_len: decompressed record length. May be bigger then result_max_len but only result_max_len bytes are produced
    result_max_len: protection against overrun
  */

static public ByteBuffer decode(ByteBuffer bytes_to_decode) {
  int pos = bytes_to_decode.position();
  ByteBuffer result = ByteBuffer.allocate(maxNoZero);
  result.order(bytes_to_decode.order()); //CALLER MUST TAKE GREAT CARE OF THIS IN MOST CASE!!!

  int rec_len = 0xFF & bytes_to_decode.get(pos);
  if (rec_len <= maxNoZero) {
    if ((pos+rec_len) > bytes_to_decode.limit()) return null;
//    bytes_to_decode.position(pos);
    for (int i=0; i < rec_len; i++){
      result.put(i, bytes_to_decode.get());
    }
//    bytes_to_decode.position(pos+1+rec_len);
  }
  else {
    rec_len = rec_len - maxNoZero -1;
    int lgPref = (rec_len+7) >> 3;
    int pr = lgPref;
    int bitp = 0;
    int bitbyte = 0;
    for (int i=0; i < rec_len; i++) {
      if (i >= maxNoZero) break;
      if ((bytes_to_decode.get(pos+1+bitbyte) & (1 << bitp)) != 0){
        result.put(i, (byte)0);
      } else {
        pr++;
        if ((pos+pr) >= bytes_to_decode.limit()) return null;
    	bytes_to_decode.position(pos+pr);
        result.put(i, bytes_to_decode.get());
      }
      bitp++;
      if (bitp > 7) {
        bitp=0;
        bitbyte++;
      }
    }
  }
  result.limit(rec_len);
  return result;
}

}
