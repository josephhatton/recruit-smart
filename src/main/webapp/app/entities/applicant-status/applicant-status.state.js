(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('applicant-status', {
        parent: 'entity',
        url: '/applicant-status',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantStatuses'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-status/applicant-statuses.html',
            controller: 'ApplicantStatusController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('applicant-status-detail', {
        parent: 'applicant-status',
        url: '/applicant-status/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantStatus'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-status/applicant-status-detail.html',
            controller: 'ApplicantStatusDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'ApplicantStatus', function ($stateParams, ApplicantStatus) {
            return ApplicantStatus.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'applicant-status',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('applicant-status-detail.edit', {
        parent: 'applicant-status-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-status/applicant-status-dialog.html',
            controller: 'ApplicantStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantStatus', function (ApplicantStatus) {
                return ApplicantStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-status.new', {
        parent: 'applicant-status',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-status/applicant-status-dialog.html',
            controller: 'ApplicantStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('applicant-status', null, {reload: 'applicant-status'});
          }, function () {
            $state.go('applicant-status');
          });
        }]
      })
      .state('applicant-status.edit', {
        parent: 'applicant-status',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-status/applicant-status-dialog.html',
            controller: 'ApplicantStatusDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantStatus', function (ApplicantStatus) {
                return ApplicantStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-status', null, {reload: 'applicant-status'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-status.delete', {
        parent: 'applicant-status',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-status/applicant-status-delete-dialog.html',
            controller: 'ApplicantStatusDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['ApplicantStatus', function (ApplicantStatus) {
                return ApplicantStatus.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-status', null, {reload: 'applicant-status'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
