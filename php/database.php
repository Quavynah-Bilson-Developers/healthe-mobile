<?php
require_once('config.php');
class MySQLDatabase{
  //db connection
  private $connection;
  //last query run in the db
  public $last_query;
  //check if real_escape_string() exist in php
  private $real_escape_string_exists;
  //hold state of magic_quotes_active (ON / OFF)
  private $magic_quotes_active;

  public function __construct(){
    //automatically open the db connection
    $this->open_connection();
    //escapes special characters in query string
    $this->magic_quotes_active=get_magic_quotes_gpc();
    //get php version
    $this->real_escape_string_exists=function_exists("mysqli_real_escape_string");
  }

  public function open_connection(){
    //open Database connection
    $this->connection=mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
    if(!$this->connection){
      die("Database connection failed:".mysql_error());
    }
  }

  public function close_connection(){
    //close database connection
    if(isset($this->connection)){
      mysqli_close($this->connection);
      unset($this->connection);
    }
  }

  public function query($sql){
    //executes query
    $this->last_query=$sql;
    $result=mysqli_query($this->connection, $sql);
    $this->confirm_query($result);
    return $result;
  }

  public function escape_value($value){
      if($this->real_escape_string_exists){
        //IF php version>=4.3.0 THEN undo magic quotes
        //effects so mysqli_real_escape_string can do the work
        if($this->magic_quotes_active){$value=stripslashes($value);}
        $value=mysqli_real_escape_string($this->connection, $value);
      }else{
        //IF php version<4.3.0 THEN
        //IF magic quotes aren't already on the add slahes manually
        //ELSE then slashes already exist
        if(!$this->magic_quotes_active){$value=addslashes($value);}
      }
      return $value;
  }

  //database-neutral  methods
  public function num_rows($result_set){
    //returns the number of rows in a result set of a query
    return mysqli_num_rows($result_set);
  }

  public function insert_id(){
    //get the last id inserted over the current db connection
    return mysqli_insert_id($this->connection);
  }

  public function affected_rows(){
    //number of rows affected by the query
    return mysqli_affected_rows($this->connection);
  }

  public function fetch_array($result_set){
    //fetch results set from the executed query
    return mysqli_fetch_array($result_set);
  }

  private function confirm_query($result){
    //checks if the query is correct
    if(!$result){
      $output="Database query failed:".mysqli_error($this->connection).
              "<br><br> Last SQl query: <i>".$this->last_query."</i>";
      die($output);
    }
  }
}

//creating a new object of the MySQLDatabase class
$database=new MySQLDatabase();
?>
