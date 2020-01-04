/*
 * @CreateTime: Mar 5, 2018 3:12 PM
 * @Author: Gavin Jaeger-Freeborn
 * @Contact: gavinfre@uvic.ca
 * @Last Modified By: Gavin Jaeger-Freeborn
 * @Last Modified Time: Mar 5, 2018 4:22 PM
 * @Student Number:T00611983
 * @ COMP 3411 Assignment 1
 * @Description: FileOrganizer will organize a record file named "student_record.txt" the file contains
 *               an integer and student name where each record is 36bytes. The record is organized from 
 *               descending order(the greater integer associated is moved up in the record list and the 
 *               lesser is moved down) this is done in a bubble sorting method where each record is checked
 *               and compared to the next record. If these records are in the wrong order they will be 
 *               rearranged. (this is repeated until no changes are made)
 */
import java.io.*;
import java.util.*;
public class FileOrganizer 
{
	/*
	 * the constructor is used to take the file check if it exists and if it does it sorts the records in descending order
	 * with the SortDescending method.
	 */
	public FileOrganizer(String filename)  throws FileNotFoundException
	{
		File inputFile;
		inputFile = new File(filename);
		if (!inputFile.exists()) 
		{ 
			System.out.println(inputFile.getName() + " does not exist"); 
			return;
		}
		SortDescending(inputFile);

	}

	/* 
	 * SortDescending basically takes the file used in the constructor and sorts the student names and numbers in descending order.
	 */
	private void SortDescending(File inputFile) throws FileNotFoundException
	{
		RandomAccessFile raf = null;
		try 
		{ 
			raf = new RandomAccessFile(inputFile, "rw"); 
			String line = "1";
			String lastline = "1";
			int StudentNumber0 = 0;
			int StudentNumber1 = 0;
			long locationOfLastLine = raf.getFilePointer();
			long locationOfCurrentLine = raf.getFilePointer();
			locationOfLastLine = raf.getFilePointer();
			locationOfLastLine = raf.getFilePointer();
			lastline = raf.readLine();
			StudentNumber0 = parseLine(lastline);
			boolean switched = true;
			boolean skip = false;

			//if a switch has occurred start from the top until no switch if preformed
			while(switched == true)
			{ 
				switched = false;
				//while we have not reached the end of the file
				while(raf.getFilePointer() < raf.length())
				{

					locationOfCurrentLine = raf.getFilePointer();
					line = raf.readLine();
					StudentNumber1 = parseLine(line);
					// if a switch is needed and we did not just restart the while loop
					if((StudentNumber1 > StudentNumber0) && (skip == false))
					{
						switched = true;
						String templine = lastline;
						raf.seek(locationOfLastLine);
						raf.write(PrepLine(line));
						raf.seek(locationOfLastLine);
						lastline = raf.readLine();
						raf.seek(locationOfCurrentLine);
						raf.write(PrepLine(templine));
						raf.seek(locationOfCurrentLine);
						line = raf.readLine();
					}// if no switch was needed continue
					else
					{
						StudentNumber0 = StudentNumber1;
						lastline = line;
						locationOfLastLine = locationOfCurrentLine;
					}
					skip = false;
				}
				// to prevent an return to the start of the record if a switch was made
				if(switched == true)
				{
					raf.seek(0);
					skip = true;
				}
			}

		}
		catch(IOException e) 
		{
			System.out.println("nope SortAccending");
		} 
		finally
		{
			// if raf contains a file
			if(!(raf == null))
			{
				try
				{
					raf.close(); // close file
				}
				catch(IOException ioe) {}
			}

		}


	}
	// PrepLine simply takes a string and makes it into an array of 36 bytes
	private byte[] PrepLine(String line)
	{
		//since a string consists of characters containing 2 bytes each
		while(line.getBytes().length  < 36)
		{
			line += " ";
		}
		return line.getBytes();
	}
	// parseLine is used to extract a number from a sting 
	static private int parseLine(String line)
	{
		Scanner scan = new Scanner(line);
		return scan.nextInt();
	}

	// main is used to create the FileOrganizer class with the appropriate student_record.txt file
	public static void main (String[] args) 
	{
		try 
		{ 
			FileOrganizer studentfile = new FileOrganizer("student_record.txt");
		}
		catch(IOException e) 
		{
			System.out.println("nope main");
		}
		try 
		{ 
			FileOrganizer studentfile = new FileOrganizer("student_record.txt");
		}
		catch(IOException e) 
		{
			System.out.println("nope main");
		}

	}
}
