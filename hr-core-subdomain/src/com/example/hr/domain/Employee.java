package com.example.hr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.ddd.Entity;

// Analysis --> Design
// Analysis: Domain/Sub-Domain/Core Sub-Domain -> Analysis Model
// Design:   Bounded-Context -> Design Model -> Ubiquitous Language -> OO Design -> Class -> Design Class 
// Ubiquitous Language {TCkimlikNo, FullName, Salary, ...} -> Bounded Context -> Core Sub-domain
// Entity Class -- 1 --> identity -> identity
//              -- 2 --> persistent
//              -- 3 --> behavior -> business method
@Entity(identity = { "identity" }, aggregate = true)
public class Employee {
	private TcKimlikNo identity;
	private FullName fullName;
	private Salary salary;
	private Iban iban;
	private BirthYear birthYear;
	private List<Department> departments;
	private JobStyle jobStyle;
	private Photo photo;

	private Employee(Builder builder) {
		this.identity = builder.identity;
		this.fullName = builder.fullName;
		this.salary = builder.salary;
		this.iban = builder.iban;
		this.birthYear = builder.birthYear;
		this.departments = builder.departments;
		this.jobStyle = builder.jobStyle;
		this.photo = builder.photo;
	}

	public TcKimlikNo getIdentity() {
		return identity;
	}

	public FullName getFullName() {
		return fullName;
	}

	public Salary getSalary() {
		return salary;
	}

	public Iban getIban() {
		return iban;
	}

	public BirthYear getBirthYear() {
		return birthYear;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public JobStyle getJobStyle() {
		return jobStyle;
	}

	public Photo getPhoto() {
		return photo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "Employee [identity=" + identity + ", fullName=" + fullName + ", salary=" + salary + ", iban=" + iban
				+ ", birthYear=" + birthYear + ", departments=" + departments + ", jobStyle=" + jobStyle + "]";
	}

	public void increaseSalary(Rate rate) {
		var newSalary = this.salary.multiply(rate.getPercent());
		// validation
		// business rules
		// invariants
		// constraints
		// policies/standards/regulations
		this.salary = newSalary;
	} 
	public static class Builder {
		private TcKimlikNo identity;
		private FullName fullName;
		private Salary salary;
		private Iban iban;
		private BirthYear birthYear;
		private List<Department> departments;
		private JobStyle jobStyle;
		private Photo photo;

		public Builder identity(String value) {
			this.identity = TcKimlikNo.of(value);
			return this;
		}

		public Builder fullName(String firstName, String lastName) {
			this.fullName = FullName.valueOf(firstName, lastName);
			return this;
		}

		public Builder salary(double value) {
			this.salary = Salary.of(value, FiatCurrency.YTL);
			return this;
		}

		public Builder salary(double value, FiatCurrency currency) {
			this.salary = Salary.of(value, currency);
			return this;
		}

		public Builder birthYear(int value) {
			this.birthYear = new BirthYear(value);
			return this;
		}

		public Builder departments(String... departments) {
			this.departments = new ArrayList<>(Arrays.stream(departments).map(Department::valueOf).toList());
			return this;
		}

		public Builder departments(List<String> departments) {
			this.departments = new ArrayList<>(departments.stream().map(Department::valueOf).toList());
			return this;
		}

		public Builder salary(double value, String currencyAsString) {
			this.salary = Salary.of(value, currencyAsString);
			return this;
		}

		public Builder iban(String value) {
			this.iban = Iban.valueOf(value);
			return this;
		}

		public Builder jobStyle(String value) {
			this.jobStyle = JobStyle.valueOf(value);
			return this;
		}

		public Builder jobStyle(JobStyle value) {
			this.jobStyle = value;
			return this;
		}

		public Builder photo(byte[] values) {
			this.photo = Photo.of(values);
			return this;
		}

		public Builder photo(String base64Values) {
			this.photo = Photo.of(base64Values);
			return this;
		}

		public Employee build() {
			// validation
			// business rules
			// invariants
			// constraints
			// policies/standards/regulations
			return new Employee(this);
		}
	}

	public void promote(Salary newSalary, List<Department> departments) {

	}

}
