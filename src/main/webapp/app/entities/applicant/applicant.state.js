(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('applicant', {
        parent: 'entity',
        url: '/applicant?page&sort&search',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'Applicants'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant/applicant.html',
            controller: 'ApplicantController',
            controllerAs: 'vm'
          }
        },
        params: {
          page: {
            value: '1',
            squash: true
          },
          sort: {
            value: 'id,asc',
            squash: true
          },
          search: null
        },
        resolve: {
          pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
            return {
              page: PaginationUtil.parsePage($stateParams.page),
              sort: $stateParams.sort,
              predicate: PaginationUtil.parsePredicate($stateParams.sort),
              ascending: PaginationUtil.parseAscending($stateParams.sort),
              search: $stateParams.search
            };
          }],
        }
      })
      .state('applicant-detail', {
        parent: 'applicant',
        url: '/applicant/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'Applicant'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant/applicant-detail.html',
            controller: 'ApplicantDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'Applicant', function ($stateParams, Applicant) {
            return Applicant.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'applicant',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('applicant-detail.edit', {
        parent: 'applicant-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-dialog.html',
            controller: 'ApplicantDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant.new', {
        parent: 'applicant',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-dialog.html',
            controller: 'ApplicantDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  firstName: null,
                  middleName: null,
                  lastName: null,
                  title: null,
                  email1: null,
                  homePhone: null,
                  cellPhone: null,
                  isDeleted: null,
                  workPhone: null,
                  email2: null,
                  referralSource: null,
                  workStatusNote: null,
                  currentSalary: null,
                  desiredSalary: null,
                  minSalary: null,
                  currentTotalComp: null,
                  currentHourlyRate: null,
                  desiredHourlyRate: null,
                  minHourlyRate: null,
                  compensationComment: null,
                  resume: null,
                  pto: null,
                  health: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('applicant', null, {reload: 'applicant'});
          }, function () {
            $state.go('applicant');
          });
        }]
      })
      .state('applicant-detail.hot-list', {
        parent: 'applicant-detail',
        url: '/detail/hot/list/{id}',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-hot-list-modal.html',
            controller: 'ApplicantHotListModalController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant', null, {reload: 'applicant'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-detail.activity', {
        parent: 'applicant-detail',
        url: '/detail/activity/{id}',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-activity-modal.html',
            controller: 'ApplicantActivityModalController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-detail.job-order', {
        parent: 'applicant-detail',
        url: '/job/order',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-job-order-dialog.html',
            controller: 'ApplicantJobOrderDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-detail.hiring-contact', {
        parent: 'applicant-detail',
        url: '/hiring/contact',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-hiring-contact-dialog.html',
            controller: 'ApplicantHiringContactDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant.edit', {
        parent: 'applicant',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-dialog.html',
            controller: 'ApplicantDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant', null, {reload: 'applicant'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant.delete', {
        parent: 'applicant',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant/applicant-delete-dialog.html',
            controller: 'ApplicantDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['Applicant', function (Applicant) {
                return Applicant.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant', null, {reload: 'applicant'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
