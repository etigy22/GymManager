classDiagram
direction BT
class ClassException
class ClassRepository {
<<Interface>>

}
class ClassRepositoryImpl
class ClassService {
<<Interface>>

}
class ClassServiceImpl {
  - ClassRepository repository
}
class ClassUI {
  - ClassService classService
  - Scanner scanner
  - EnrollmentService enrollmentService
}
class DatabaseInitializer {
  - Logger logger
}
class DatabaseUtil {
  - List~Connection~ activeConnections
  - String DB_URL
  - boolean driverLoaded
}
class EnrollmentException
class EnrollmentRepository {
<<Interface>>

}
class EnrollmentRepositoryImpl
class EnrollmentService {
<<Interface>>

}
class EnrollmentServiceImpl {
  - EnrollmentRepository enrollmentRepository
  - MemberRepository memberRepository
  - ClassRepository classRepository
}
class GymApp
class GymClass {
  - int classId
  - String instructorFirstName
  - String name
  - String instructorLastName
  - int maxCapacity
}
class Member {
  - int memberId
  - String membershipType
}
class MemberException
class MemberRepository {
<<Interface>>

}
class MemberRepositoryImpl
class MemberService {
<<Interface>>

}
class MemberServiceImpl {
  - MemberRepository repository
}
class MemberUI {
  - MemberService memberService
  - Scanner scanner
}
class Person {
  # String lastName
  # String firstName
}
class ServiceException

ClassException  -->  ServiceException 
ClassRepositoryImpl  ..>  ClassRepository 
ClassRepositoryImpl  ..>  GymClass : «create»
ClassServiceImpl "1" *--> "repository 1" ClassRepository 
ClassServiceImpl  ..>  ClassService 
ClassServiceImpl  ..>  GymClass : «create»
ClassUI "1" *--> "classService 1" ClassService 
ClassUI "1" *--> "enrollmentService 1" EnrollmentService 
EnrollmentException  -->  ServiceException 
EnrollmentRepositoryImpl  ..>  EnrollmentRepository 
EnrollmentServiceImpl "1" *--> "classRepository 1" ClassRepository 
EnrollmentServiceImpl "1" *--> "enrollmentRepository 1" EnrollmentRepository 
EnrollmentServiceImpl  ..>  EnrollmentService 
EnrollmentServiceImpl "1" *--> "memberRepository 1" MemberRepository 
GymApp  ..>  ClassRepositoryImpl : «create»
GymApp  ..>  ClassServiceImpl : «create»
GymApp  ..>  ClassUI : «create»
GymApp  ..>  EnrollmentRepositoryImpl : «create»
GymApp  ..>  EnrollmentServiceImpl : «create»
GymApp  ..>  MemberRepositoryImpl : «create»
GymApp  ..>  MemberServiceImpl : «create»
GymApp  ..>  MemberUI : «create»
Member  -->  Person 
MemberException  -->  ServiceException 
MemberRepositoryImpl  ..>  Member : «create»
MemberRepositoryImpl  ..>  MemberRepository 
MemberServiceImpl  ..>  Member : «create»
MemberServiceImpl "1" *--> "repository 1" MemberRepository 
MemberServiceImpl  ..>  MemberService 
MemberUI "1" *--> "memberService 1" MemberService 
